package com.shoescompany.application.services.implementations;

import com.shoescompany.application.services.interfaces.ICategoryService;
import com.shoescompany.domain.dtos.CategoryDTO;
import com.shoescompany.domain.entities.Category;
import com.shoescompany.domain.enums.State;
import com.shoescompany.domain.records.CategoryResponse;
import com.shoescompany.infrastructure.repositories.CategoryRepository;
import com.shoescompany.infrastructure.utils.ModelMapperUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapperUtils modelMapperUtils;

    public CategoryService(CategoryRepository categoryRepository, ModelMapperUtils modelMapperUtils) {
        this.categoryRepository = categoryRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Category findByCategory(Long id) throws Exception {
        return this.categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found"));
    }

    @Override
    @Cacheable(value = "categories", key = "'all'")
    public List<CategoryResponse> findAll() {
        return this.categoryRepository.findAll().stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "categories", key = "#id")
    public CategoryResponse findById(Long id) throws Exception {
        return mapToCategoryResponse(findByCategory(id));
    }

    @Override
    public CategoryResponse save(CategoryDTO categoryDTO) {
        Category categoryData = modelMapperUtils.map(categoryDTO, Category.class);
        Category category = categoryRepository.save(categoryData);
        return mapToCategoryResponse(category);
    }

    @Override
    @CachePut(value = "categories", key = "#id")
    public void update(Long id, CategoryDTO categoryDTO) throws Exception {
        Category category = findByCategory(id);
        modelMapperUtils.mapVoid(categoryDTO, category);
        categoryRepository.save(category);
    }

    @Override
    @CacheEvict(value = "categories", key = "#id")
    public void delete(Long id) throws Exception {
        changeState(id, State.Inactivo);
    }

    @Override
    @CachePut(value = "categories", key = "#id")
    public void activate(Long id) throws Exception {
        changeState(id, State.Activo);
    }

    private void changeState(Long id, State state) throws Exception {
        Category category = findByCategory(id);
        category.setState(state);
        categoryRepository.save(category);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCategory(),
                category.getImage()
        );
    }
}
