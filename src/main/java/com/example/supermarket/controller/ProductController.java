package com.example.supermarket.controller;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//TO DO 
// Migliorare gli header delle richieste
// Mostrare le categorie nella findAll dei product
@RestController
@RequestMapping(path = "/products/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public Page<Product> findAll(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) String sortDirection,
                                 @RequestParam(required = false) String dataType) throws EntityNotFoundException {
        return productService.findAllProductsSorted(page, sortDirection, dataType);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody Product product) {
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
    public Product findById(@NotNull @RequestParam int id) {
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
        return productService.findByQuantity(quantity);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        productService.deleteAll();
        return ResponseEntity.ok("All products have been deleted");
    }

    @DeleteMapping("/id/")
    public ResponseEntity<String> deleteById(@NotNull @RequestParam int id) {
        productService.deleteById(id);
        return ResponseEntity.ok("User with id " + id + " have been deleted");
    }

    // CATEGORY
    @GetMapping("/all/category")
    public List<Category> findAllCategories() {
        return productService.findAllCategories();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> removeCategoryFromProduct(@PathVariable int productId) {
        productService.removeCategoryFromProduct(productId);
        return ResponseEntity.ok("Category removed successfully");
    }

    @DeleteMapping("/category")
    public ResponseEntity<String> deleteCategoryById(int categoryId) {
        productService.deleteCategoryById(categoryId);
        return ResponseEntity.ok("Category with id " + categoryId + " delete successfully");
    }
}
