package com.example.supermarket.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.supermarket.entity.Product;
import com.example.supermarket.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//TO DO 
// Migliorare gli header delle richieste
// Mostrare le categorie nella findAll dei product
@RestController
@RequestMapping(path = "/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<Product> findAll() throws EntityNotFoundException {
        return productService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNewProduct(@Valid @ModelAttribute Product product) {
        try {
            productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product save successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping(path = "/id/")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product modProduct, @RequestParam int id) {
        try {
            productService.updateProduct(id, modProduct);
            return ResponseEntity.ok(modProduct.getName() + " updated successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body((e.getMessage()));
        }
    }

    @GetMapping("/id/")
    public Optional<Product> findById(@NotNull @RequestParam int id) {
        return productService.findById(id);
    }

    @GetMapping("/name/")
    public List<Product> findByName(@NotBlank @RequestParam String name) {
        return productService.findByName(name);
    }

    @GetMapping("/sellingPrice/")
    public List<Product> findBySellingPrice(@NotNull @RequestParam double sellingPrice) {
        return productService.findBySellingPrice(sellingPrice);
    }

    @GetMapping("/category/name/")
    public List<Product> findByCategoryName(@NotBlank @RequestParam String name) {
        return productService.findByCategoryName(name);
    }

    @GetMapping("/supplier/name/")
    public List<Product> findBySupplierName(@NotBlank @RequestParam String name) {
        return productService.findBySupplierName(name);
    }

    @GetMapping("/expirationDate/")
    public List<Product> findByExpirationDate(@NotNull @RequestParam LocalDate expirationDate) {
        return productService.findByExpirationDate(expirationDate);
    }

    @GetMapping("/stock/quantity/")
    public List<Product> findByStockQuantity(@NotNull @RequestParam int quantity) {
        return productService.findByStockQuantity(quantity);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() {
        productService.deleteAll();
        return ResponseEntity.ok("All products have been deleted");
    }

    @DeleteMapping("/id/")
    public ResponseEntity<?> deleteById(@NotNull @RequestParam int id) {
        productService.deleteById(id);
        return ResponseEntity.ok("User with id " + id + " have been deleted");
    }

}
