package com.example.supermarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.supermarket.entity.Category;
import com.example.supermarket.service.CategoryService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@Transactional
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> createNewCategory(@Valid @ModelAttribute Category category) {

        try {
            categoryService.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @GetMapping("/all")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/id/")
    public Category findById(@RequestParam int id) {
        return categoryService.findById(id);
    }

    @GetMapping("/name/")
    public Category findByName(@RequestParam String name) {
        return categoryService.findByName(name);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() {
        categoryService.deleteAll();
        return ResponseEntity.ok("All categories have been deleted");
    }

    @DeleteMapping("/id/")
    public ResponseEntity<?> deleteById(@RequestParam int id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok("Category with id " + id + " have been deleted");
    }

}
