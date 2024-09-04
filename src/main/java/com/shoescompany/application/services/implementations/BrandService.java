package com.shoescompany.application.services.implementations;

import com.shoescompany.application.services.interfaces.IBrandService;
import com.shoescompany.domain.dtos.BrandDTO;
import com.shoescompany.domain.entities.Brand;
import com.shoescompany.domain.entities.Category;
import com.shoescompany.domain.enums.State;
import com.shoescompany.domain.records.BrandResponse;
import com.shoescompany.domain.records.CategoryResponse;
import com.shoescompany.infrastructure.repositories.BrandRepository;
import com.shoescompany.infrastructure.utils.ModelMapperUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    private final ModelMapperUtils modelMapperUtils;

    public BrandService(BrandRepository brandRepository, ModelMapperUtils modelMapperUtils) {
        this.brandRepository = brandRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Brand findByBrand(Long id) throws Exception {
        return this.brandRepository.findById(id).orElseThrow();
    }

    @Override
    @Cacheable(value = "brands", key = "'all'")
    public List<BrandResponse> findAll() {
        List<Brand> brands = this.brandRepository.findAll();
        return brands.stream()
                .map(this::mapToBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "brands", key = "#id")
    public BrandResponse findById(Long id) throws Exception {
        Brand brand = findByBrand(id);
        return mapToBrandResponse(findByBrand(id));
    }

    @Override
    public BrandResponse save(BrandDTO brandDTO) {
        Brand brand = modelMapperUtils.map(brandDTO, Brand.class);
        Brand savedBrand = brandRepository.save(brand);
        return mapToBrandResponse(savedBrand);
    }

    @Override
    @CachePut(value = "brands", key = "#id")
    public void update(Long id, BrandDTO brandDTO) throws Exception {
        Brand brand = findByBrand(id);
        modelMapperUtils.mapVoid(brandDTO, brand);
        brandRepository.save(brand);
    }

    @Override
    @CacheEvict(value = "brands", key = "#id")
    public void delete(Long id) throws Exception {
        changeState(id, State.Inactivo);
    }

    @Override
    @CachePut(value = "brands", key = "#id")
    public void activate(Long id) throws Exception {
        changeState(id, State.Activo);
    }

    private void changeState(Long id, State state) throws Exception {
        Brand brand = findByBrand(id);
        brand.setState(state);
        brandRepository.save(brand);
    }

    private BrandResponse mapToBrandResponse(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getBrand()
        );
    }
}