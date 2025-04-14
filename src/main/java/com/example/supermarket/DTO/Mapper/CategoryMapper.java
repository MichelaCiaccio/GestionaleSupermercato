package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.CategoryDTO;
import com.example.supermarket.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toCategoryDTO(Category category);
}
