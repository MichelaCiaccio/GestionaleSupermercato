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

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}
