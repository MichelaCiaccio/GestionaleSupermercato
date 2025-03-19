package com.example.supermarket.service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    /**
     * This method updates a product identified by its ID.
     * At first checks if the product exists, and if it doesn't, throws an
     * EntityNotFoundException.
     * Then it checks if the new category exists, and if it doesn't, throws an EntityNotFoundException
     * Otherwise, it proceeds to update the product's attribute with the new
     * information and saves the modified product.
     *
     * @param id         The ID of the product to be updated.
     * @param modProduct The new product data to update with.
     */
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

    /**
     * This method searches for all the product, organizes them into pagination of 20 element, and sorts them according
     * to a specified direction and data type.
     * If the page number, sort direction or the data type are not provided by the client default values are set.
     * Check if any product exists and returns them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display. If null, the first page (0) is used.
     * @param sortDirection The direction in which the client wants the products to be ordered. Defaults to "ASC" if null or blank.
     * @param dataType      The data by which the products should be ordered. Defaults to "name" if null or blank.
     * @return A Page containing the list of products.
     */
    public Page<Product> findAllProductsSorted(Integer page, String sortDirection, String dataType) {
        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "name" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Product> products = productRepository.findAll(pageable);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("There are no products");
        }
        return products;
    }

    /**
     * This method searches for a product by its ID.
     * If no product is found throws a EntityNotFoundException.
     * The found product are returned.
     *
     * @param id The id of the product to search for
     * @return The product found
     */
    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    }

    /**
     * This method searches for products by their name.
     * If no products are found throws a EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param name The name of the products
     * @return The products found
     */
    public List<Product> findByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Product with name " + name + " not found");
        }
        return products;
    }

    /**
     * This method searches for products by the name of their category.
     * If no products are found throws a EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param categoryName The name of the category
     * @return The products found
     */
    public List<Product> findByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a category with the name " + categoryName);
        }
        return products;
    }

    /**
     * This method searches for products by their selling price.
     * If no products are found throws a EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param sellingPrice The selling price of the products
     * @return the products found
     */
    public List<Product> findBySellingPrice(double sellingPrice) {
        List<Product> products = productRepository.findBySellingPrice(sellingPrice);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a selling price equal to " + sellingPrice);
        }
        return products;
    }

    /**
     * This method searches for products by the name of their supplier.
     * If no products are found throws a EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param supplierName The name of the supplier
     * @return The products found
     */
    public List<Product> findBySupplierName(String supplierName) {
        List<Product> products = productRepository.findByStocks_Supplier_Name(supplierName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a supplier with a name" + supplierName);
        }
        return products;
    }

    /**
     * This method searches for products by their expiration date.
     * If no products are found throws a EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param expirationDate The expiration date of the products
     * @return The products found
     */
    public List<Product> findByExpirationDate(LocalDate expirationDate) {
        List<Product> products = productRepository.findByStocks_ExpirationDate(expirationDate);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has expiration date equals to " + expirationDate);
        }
        return products;
    }

    /**
     * This method searches for products by their quantity in stock.
     * If no products are found throws a EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param quantity The quantity in stock
     * @return the products found
     */
    public List<Product> findByStockQuantity(int quantity) {
        List<Product> products = productRepository.findByStocks_Quantity(quantity);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product is available in quantity equals to " + quantity);
        }
        return products;
    }

    /**
     * This method deletes a product identified by its id.
     * If the product exists proceed to delete it.
     * If not, an EntityNotFoundException is thrown.
     *
     * @param id The id of the product to delete
     */
    public void deleteById(int id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("No product with this id to delete");
        }
        productRepository.deleteById(id);
    }

    /**
     * This method delete all the products.
     * Checks if the products exist, and if they don't, throws an
     * EntityNotFoundException.
     * Otherwise, it proceeds to delete them all.
     */
    public void deleteAll() {
        if (productRepository.findAll().isEmpty()) {
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
