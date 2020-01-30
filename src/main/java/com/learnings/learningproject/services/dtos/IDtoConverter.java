package com.learnings.learningproject.services.dtos;

public interface IDtoConverter<T, TDto> {
    TDto convertToDto(T entity);
    T convertToEntity(TDto dto);
}
