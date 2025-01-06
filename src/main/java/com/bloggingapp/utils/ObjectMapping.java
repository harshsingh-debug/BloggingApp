package com.bloggingapp.utils;

import org.modelmapper.ModelMapper;

public class ObjectMapping {
    private final ModelMapper modelMapper;

    public ObjectMapping(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <S, D> D modelMapping(S sourceObject, Class<D> destinationClass) {
        return this.modelMapper.map(sourceObject, destinationClass);
    }
}
