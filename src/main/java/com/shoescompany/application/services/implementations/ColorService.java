package com.shoescompany.application.services.implementations;


import com.shoescompany.application.services.interfaces.IColorService;
import com.shoescompany.domain.dtos.ColorDTO;
import com.shoescompany.domain.entities.Color;
import com.shoescompany.domain.enums.State;
import com.shoescompany.domain.records.ColorResponse;
import com.shoescompany.infrastructure.repositories.ColorRepository;
import com.shoescompany.infrastructure.utils.ModelMapperUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorService implements IColorService {

    private final ColorRepository colorRepository;
    private final ModelMapperUtils modelMapperUtils;

    public ColorService(ColorRepository colorRepository, ModelMapperUtils modelMapperUtils) {
        this.colorRepository = colorRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Color findByColor(Long id) throws Exception {
        return this.colorRepository.findById(id).orElseThrow(() -> new Exception("Color not found"));
    }

    @Override
    @Cacheable(value = "colors", key = "'all'")
    public List<ColorResponse> findAll() {
        return this.colorRepository.findAll().stream()
                .map(this::mapToColorResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "colors", key = "#id")
    public ColorResponse findById(Long id) throws Exception {
        return mapToColorResponse(findByColor(id));
    }

    @Override
    public ColorResponse save(ColorDTO colorDTO) {
        Color colorData = modelMapperUtils.map(colorDTO, Color.class);
        Color color = colorRepository.save(colorData);
        return mapToColorResponse(color);
    }

    @Override
    @CachePut(value = "colors", key = "#id")
    public void update(Long id, ColorDTO colorDTO) throws Exception {
        Color color = findByColor(id);
        modelMapperUtils.mapVoid(colorDTO, color);
        colorRepository.save(color);
    }

    @Override
    @CacheEvict(value = "colors", key = "#id")
    public void delete(Long id) throws Exception {
        changeState(id, State.Inactivo);
    }

    @Override
    @CachePut(value = "colors", key = "#id")
    public void activate(Long id) throws Exception {
        changeState(id, State.Activo);
    }

    private void changeState(Long id, State state) throws Exception {
        Color color = findByColor(id);
        color.setState(state);
        colorRepository.save(color);
    }

    private ColorResponse mapToColorResponse(Color color) {
        return new ColorResponse(
                color.getId(),
                color.getColor()   // Assuming the field in Color entity is hexCode
        );
    }
}
