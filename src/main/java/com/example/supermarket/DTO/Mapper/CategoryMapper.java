package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.CategoryDTO;
import com.example.supermarket.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getName());
    }
}
