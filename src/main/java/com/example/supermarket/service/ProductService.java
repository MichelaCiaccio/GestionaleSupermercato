package com.example.supermarket.service;

import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.User;
import com.example.supermarket.repo.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
        return product;

    }

    public List<Product> findByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Product with name " + name + " not found");
        }
        return products;
    }

    public List<Product> findByCategory(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a category with the name " + categoryName);
        }
        return products;
    }

    public List<Product> findBySellingPrice(double sellingPrice) {
        List<Product> products = productRepository.findBySellingPrice(sellingPrice);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a selling price equal to " + sellingPrice);
        }
        return products;
    }

}
