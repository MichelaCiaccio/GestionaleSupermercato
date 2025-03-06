package com.example.supermarket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Product;
import com.example.supermarket.repo.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product) {
        if (productRepository.existsById(product.getId())) {
            throw new DataIntegrityViolationException("Product with id " + product.getId() + " already exists");
        }
        productRepository.save(product);
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new EntityNotFoundException("There are no products");
        }
        return products;
    }

    public Optional<Product> findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
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

    public List<Product> findByCategoryName(String categoryName) {
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

    public List<Product> findBySupplierName(String supplierName) {
        List<Product> products = findBySupplierName(supplierName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a supplier with a name" + supplierName);
        }
        return products;
    }

    public List<Product> findByExpirationDate(LocalDate expirationDate) {
        List<Product> products = productRepository.findByStocks_ExpirationDate(expirationDate);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has expiration date equals to " + expirationDate);
        }
        return products;
    }

    public List<Product> findByStockQuantity(int quantity) {
        List<Product> products = productRepository.findByStocks_Quantity(quantity);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product is available in quantity equals to " + quantity);
        }
        return products;
    }

    public void deleteById(int id) {
        if (findById(id).isEmpty()) {
            throw new EntityNotFoundException("No product with this id to delete");
        }
        productRepository.deleteById(id);
    }

    public void deleteAll() {
        if (findAll().isEmpty()) {
            throw new EntityNotFoundException("There are no products to delete");
        }
        productRepository.deleteAll();
    }

}
