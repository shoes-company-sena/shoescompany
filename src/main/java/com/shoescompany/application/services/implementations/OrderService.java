package com.shoescompany.application.services.implementations;

import com.shoescompany.application.services.interfaces.IOrderService;
import com.shoescompany.domain.dtos.OrderDTO;
import com.shoescompany.domain.entities.Order;
import com.shoescompany.domain.entities.Product;
import com.shoescompany.domain.enums.State;
import com.shoescompany.domain.records.OrderResponse;
import com.shoescompany.infrastructure.repositories.OrderRepository;
import com.shoescompany.infrastructure.repositories.ProductRepository;
import com.shoescompany.infrastructure.utils.ModelMapperUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapperUtils modelMapperUtils;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, ModelMapperUtils modelMapperUtils) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Order findByOrder(Long id) throws Exception {
        return this.orderRepository.findById(id).orElseThrow(() -> new Exception("Order no encontrado"));
    }

    @Override
    @Cacheable(value = "orders", key = "'all'")
    public List<OrderResponse> findAll() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "orders", key = "#id")
    public OrderResponse findById(Long id) throws Exception {
        Order order = findByOrder(id);
        return mapToOrderResponse(order);
    }

    @Override
    public OrderResponse save(OrderDTO orderDTO) {
        Order order = modelMapperUtils.map(orderDTO, Order.class);
        Set<Product> products = (Set<Product>) productRepository.findAllById(orderDTO.getProductIds());
        order.setProducts(products);
        Order savedOrder = orderRepository.save(order);
        return mapToOrderResponse(savedOrder);
    }

    @Override
    @CachePut(value = "orders", key = "#id")
    public void update(Long id, OrderDTO orderDTO) throws Exception {
        Order order = findByOrder(id);
        modelMapperUtils.mapVoid(orderDTO, order);
        Set<Product> products = (Set<Product>) productRepository.findAllById(orderDTO.getProductIds());
        order.setProducts(products);
        orderRepository.save(order);
    }

    @Override
    @CacheEvict(value = "orders", key = "#id")
    public void delete(Long id) throws Exception {
        changeState(id, State.Inactivo);
    }

    public void activate(Long id) throws Exception {
        changeState(id, State.Activo);
    }

    private void changeState(Long id, State state) throws Exception {
        Order order = findByOrder(id);
        order.setState(state);
        orderRepository.save(order);
    }


    private OrderResponse mapToOrderResponse(Order order) {
        Set<Long> productIds = order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getQuantity(),
                productIds
        );
    }
}
