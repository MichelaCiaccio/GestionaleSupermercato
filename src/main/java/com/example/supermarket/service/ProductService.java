package com.example.supermarket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // TO DO il controllo non funziona bisogna migliorarlo e poi spostarlo in una
    // funzione autonoma
    public void save(Product product) {
        if (product.getStocks() != null) {
            for (Stock stock : product.getStocks()) {
                if (productRepository.existsByNameAndStocks_Supplier_Name(product.getName(),
                        stock.getSupplier().getName())) {
                    throw new DuplicateKeyException("Product with name " + product.getId() + " already exist");
                }
            }
        }
        Optional<Category> existingCategory = categoryRepository.findByName(product.getCategory().getName());
        Category category = existingCategory.orElseGet(() -> categoryRepository.save(product.getCategory()));
        product.setCategory(category);
        productRepository.save(product);

    }

    public void updateProduct(int id, Product modProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));

        Category category = categoryRepository.findByName(modProduct.getCategory().getName())
                .orElse(categoryRepository.save(modProduct.getCategory()));

        product.setCategory(category);
        product.setCategory(category);
        product.setName(modProduct.getName());
        product.setSellingPrice(modProduct.getSellingPrice());
        product.setStocks(modProduct.getStocks());
        productRepository.save(product);
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll(PageRequest.of(0, 20)).getContent();
        if (products.isEmpty()) {
            throw new EntityNotFoundException("There are no products");
        }
        return products;
    }

    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
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
        List<Product> products = productRepository.findByStocks_Supplier_Name(supplierName);
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
        if (productRepository.findById(id).isEmpty()) {
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

    // CATEGORY

    public List<Category> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("There are no categories");
        }
        return categoryRepository.findAll();
    }

    public void removeCategoryFromProduct(int productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("There are no products with id " + productId));
        product.setCategory(null);
        productRepository.save(product);
    }

    public void deleteCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        List<Product> products = productRepository.findByCategoryName(category.get().getName());
        if (category.isEmpty()) {
            throw new EntityNotFoundException("There are no category with id " + id + " to delete");
        }
        for (Product product : products) {
            removeCategoryFromProduct(product.getId());
        }

        categoryRepository.deleteById(id);
    }

}
