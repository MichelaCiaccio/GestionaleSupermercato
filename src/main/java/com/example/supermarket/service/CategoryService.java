package com.example.supermarket.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.supermarket.entity.Category;
import com.example.supermarket.repo.CategoryRepository;

public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(int id) {
        return categoryRepository.findById(id);
    }
}
