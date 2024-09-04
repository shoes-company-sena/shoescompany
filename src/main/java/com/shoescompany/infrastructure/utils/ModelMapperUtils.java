package com.shoescompany.infrastructure.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtils {

    private final ModelMapper modelMapper;

    public ModelMapperUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public  <S, T> void mapVoid(S source, T targetClass) {
         modelMapper.map(source, targetClass);
    }


}
