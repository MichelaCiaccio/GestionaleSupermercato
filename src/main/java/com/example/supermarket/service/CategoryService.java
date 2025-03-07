package com.example.supermarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.repo.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("There are no categories");
        }
        return categories;
    }

    public Category findById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Categoy with name " + name + " not found"));
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void deleteAll() {
        if (findAll().isEmpty()) {
            throw new EntityNotFoundException("There are no categories to delete");
        }
        categoryRepository.deleteAll();
    }

    public void deleteById(int id) {
        if (findById(id) == null) {
            throw new EntityNotFoundException("There is no cartegory with id" + id + " to delete");
        }
        categoryRepository.deleteById(id);
    }
}
