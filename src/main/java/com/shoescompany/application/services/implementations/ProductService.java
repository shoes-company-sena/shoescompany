package com.shoescompany.application.services.implementations;

import com.shoescompany.application.services.interfaces.IProductService;
import com.shoescompany.domain.dtos.ProductDTO;
import com.shoescompany.domain.entities.Brand;
import com.shoescompany.domain.entities.Category;
import com.shoescompany.domain.entities.Color;
import com.shoescompany.domain.entities.Product;
import com.shoescompany.domain.enums.State;
import com.shoescompany.domain.records.BrandResponse;
import com.shoescompany.domain.records.ColorResponse;
import com.shoescompany.domain.records.ProductResponse;
import com.shoescompany.infrastructure.repositories.BrandRepository;
import com.shoescompany.infrastructure.repositories.CategoryRepository;
import com.shoescompany.infrastructure.repositories.ProductRepository;
import com.shoescompany.infrastructure.utils.ModelMapperUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ModelMapperUtils modelMapperUtils;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ModelMapperUtils modelMapperUtils) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Product findByProduct(Long id) throws Exception {
        return this.productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
    }

    @Override
    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponse> findAll() {
        return this.productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponse findById(Long id) throws Exception {
        return mapToProductResponse(findByProduct(id));
    }

    @Override
    public ProductResponse save(ProductDTO productDTO) throws Exception {
        Category category = categoryRepository.findById(productDTO.getCategory())
                .orElseThrow(() -> new Exception());

        Brand brand = this.brandRepository.findById(productDTO.getBrand()).orElseThrow();

        Product productData = modelMapperUtils.map(productDTO, Product.class);
        productData.setCategory(category);
        productData.setBrand(brand);

        Product product = productRepository.save(productData);
        return mapToProductResponse(product);
    }

    @Override
    @CachePut(value = "products", key = "#id")
    public void update(Long id, ProductDTO productDTO) throws Exception {
        Product product = findByProduct(id);
        modelMapperUtils.mapVoid(productDTO, product);
        productRepository.save(product);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) throws Exception {
        changeState(id, State.Inactivo);
    }

    @Override
    @CachePut(value = "products", key = "#id")
    public void activate(Long id) throws Exception {
        changeState(id, State.Activo);
    }

    private void changeState(Long id, State state) throws Exception {
        Product product = findByProduct(id);
        product.setState(state);
        productRepository.save(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProduct(),
                product.getDescription(),
                product.getGender(),
                product.getBrand().getBrand(),
                product.getPrice(),
                product.getImage(),
                product.getSize(),
//                product.getColors().stream()
//                        .map(color -> new ColorResponse(color.getId(), color.getColor()))
//                        .collect(Collectors.toSet()),
                product.getCategory().getCategory()

        );
    }
}