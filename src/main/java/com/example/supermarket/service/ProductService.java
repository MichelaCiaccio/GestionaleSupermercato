package com.example.supermarket.service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductSaleRepository productSaleRepository;

    @Autowired
    private SupplierService supplierService;


    /**
     * This method creates a new product, along with its associated stock and supplier.
     * Checks if the supplier already exists if it does associate with the stock of that product,
     * otherwise it creates it.
     * Check if a product-supplier combination already exists if it does throw a
     * DuplicateRequestException.
     * It associated every combination to stock and save the new product
     *
     * @param product The product to be saved
     */
    @Transactional
    public void save(Product product) {

        // Creo una Lista di Stock
        List<Stock> stocks = new ArrayList<>();


        // Itero sugli stock del prodotto
        for (Stock stock : product.getStocks()) {

            //Controllo se il supplier esiste, in caso contrario lo creo e lo setto nello stock
            stock.setSupplier(supplierService.createNewSupplier(stock.getSupplier()));


            // Controllo se nello stock esiste già una coppia prodotto-fornitore
            Optional<Product> existingProduct =
                    productRepository.findByNameAndStocks_Supplier_Id(product.getName(),
                                                                      stock.getSupplier().getId());
            existingProduct.ifPresent(value -> value.setRemoved(false));


            // Per ogni stock setto il prodotto, aggiungo lo stock alla Lista di
            // stocks
            stock.setProduct(product);
            stocks.add(stock);

        }

        // Controllo se la categoria esiste già e se non esiste la creo e la setto al prodotto
        Category existingCategory =
                categoryRepository.findByName(product.getCategory().getName()).orElseGet(() -> categoryRepository.save(product.getCategory()));

        product.setCategory(existingCategory);

        // Setto la lista di stock del prodotto
        product.setStocks(stocks);

        // Salvo il prodotto
        productRepository.save(product);
    }

    /**
     * This method updates a product identified by its ID.
     * At first checks if the product exists, and if it doesn't, throws an
     * EntityNotFoundException.
     * Then it checks if the new category exists, and if it doesn't, throws an
     * EntityNotFoundException
     * Otherwise, it proceeds to update the product's attribute with the new
     * information and saves the modified product.
     *
     * @param id         The ID of the product to be updated.
     * @param modProduct The new product data to update with.
     */
    // To do Non funziona,
    // errore : org.hibernate.TransientObjectException:
    // persistent instance references an unsaved transient instance of 'com.example.supermarket
    // .entity.Product'
    // (save the transient instance before flushing)
    public void updateProduct(int id, Product modProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not " +
                                                                       "found"));

        Category category = categoryRepository.findByName(modProduct.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(modProduct.getCategory()));

        List<Stock> stocks = new ArrayList<>();

        for (Stock stock : modProduct.getStocks()) {
            Optional<Supplier> modSupplier =
                    supplierRepository.findByName(stock.getSupplier().getName());
            if (modSupplier.isEmpty()) {
                Supplier newSupplier = supplierService.createNewSupplier(stock.getSupplier());
                stock.setSupplier(newSupplier);
            } else {
                stock.setSupplier(modSupplier.get());
            }


            stock.setProduct(modProduct);
            stock.setQuantity(stock.getQuantity());
            stock.setDeliveryDate(stock.getDeliveryDate());
            stock.setExpirationDate(stock.getExpirationDate());
            stocks.add(stock);

        }

        product.setCategory(category);
        product.setName(modProduct.getName());
        product.setSellingPrice(modProduct.getSellingPrice());
        product.setStocks(stocks);
        productRepository.save(product);
    }

    /**
     * This method searches for all the product, organizes them into pagination of 20 elements,
     * and sorts them according
     * to a specified direction and data type.
     * If the page number, sort direction or the data type are not provided by the client,
     * default values are set.
     * Check if any product exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display.
     * @param sortDirection The direction in which the client wants the products to be ordered.
     *                      Defaults to "ASC" if null or blank.
     * @param dataType      The data by which the products should be ordered.
     * @return A Page containing the list of products.
     */
    public Page<Product> findAllProductsSorted(Integer page, String sortDirection,
                                               String dataType, boolean showRemoved) {
        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "name" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Product> products = showRemoved ? productRepository.findAll(pageable) :
                productRepository.findByRemovedFalse(pageable);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("There are no products");
        }
        return products;

    }


    // FIND BY

    /**
     * This method searches for a product by its ID.
     * If no product is found, throw an EntityNotFoundException.
     * The found product is returned.
     *
     * @param id The id of the product to search for
     * @return The product found
     */
    public Product findById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not " +
                                                                       "found"));
    }

    /**
     * This method searches for products by their name.
     * If no products are found, throw an EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param name The name of the products
     * @return The products found
     */
    public List<Product> findByName(String name) {
        List<Product> products = productRepository.findByNameAndRemovedFalse(name);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Product with name " + name + " not found");
        }
        return products;
    }

    /**
     * This method searches for products by the name of their category.
     * If no products are found, throw an EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param categoryName The name of the category
     * @return The products found
     */
    public List<Product> findByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryNameAndRemovedFalse(categoryName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a category with the name " + categoryName);
        }
        return products;
    }

    /**
     * This method searches for products by their selling price.
     * If no products are found, throw an EntityNotFoundException.
     * Otherwise, a list of found products is returned
     *
     * @param sellingPrice The selling price of the products
     * @return the products found
     */
    public List<Product> findBySellingPrice(double sellingPrice) {
        List<Product> products = productRepository.findBySellingPriceAndRemovedFalse(sellingPrice);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a selling price equal to " + sellingPrice);
        }
        return products;
    }

    /**
     * This method searches for products by the name of their supplier.
     * If no products are found, throw an EntityNotFoundException.
     * Otherwise, a list of found products is returned.
     *
     * @param supplierName The name of the supplier
     * @return The products found
     */
    public List<Product> findBySupplierName(String supplierName) {
        List<Product> products =
                productRepository.findByStocks_Supplier_NameAndRemovedFalse(supplierName);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product has a supplier with a name " + supplierName);
        }
        return products;
    }

    /**
     * This method searches for products that have specified stock quantity.
     * If no products are found, throw an EntityNotFoundException.
     * Otherwise, a List of found products is returned.
     *
     * @param quantity The stock quantity of the products
     * @return The List of the products found
     */
    public List<Product> findByQuantity(int quantity) {
        List<Product> products = productRepository.findByStocks_QuantityAndRemovedFalse(quantity);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product found with stock quantity  " + quantity);
        }
        return products;
    }

    /**
     * This method searches for product that has specified expiration date.
     * If no products are found, throw an EntityNotFoundException.
     * Otherwise, a List of found products is returned.
     *
     * @param expirationDate The expiration date of the products
     * @return The list of the products found
     */
    public List<Product> findByExpirationDate(LocalDate expirationDate) {
        List<Product> products =
                productRepository.findByStocks_ExpirationDateAndRemovedFalse(expirationDate);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("No product found with expiration date : " + expirationDate);
        }
        return products;
    }

    /**
     * This method deletes a product identified by its id.
     * If the product exists, proceed to delete it.
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
     * This method deletes all the products.
     * Checks if the products exist, and if they don't, throws an
     * EntityNotFoundException.
     * Otherwise, it proceeds to delete them all.
     */
    @Transactional
    public void deleteAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new EntityNotFoundException("There are no products to delete");
        }
        productSaleRepository.deleteAll();
        productRepository.deleteAll();
        supplierRepository.deleteAll();
        saleRepository.deleteAll();

    }

    /**
     * The method searches for all the categories
     * If no categories are found it throws an EntityNotFoundException.
     * Otherwise, a list of the found categories is returned.
     *
     * @return The categories found
     */
    public List<Category> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("There are no categories");
        }
        return categoryRepository.findAll();
    }

    // CATEGORY

    /**
     * This method remove the category from a product identified by its id.
     * If the product doesn't exist, an EntityNotFoundException is thrown.
     * Otherwise, proceed to save the product with category set to null.
     *
     * @param productId The ID of the product from which to remove the category
     */
    public void removeCategoryFromProduct(int productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("There are no products with id " + productId));
        product.setCategory(null);
        productRepository.save(product);
    }

    /**
     * This method deletes a category identified by its id.
     * If the category doesn't exist, an EntityNotFoundException is thrown.
     * Otherwise, it removes the category from all associated products and deletes the category.
     *
     * @param id The ID of the category to delete
     */
    public void deleteCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new EntityNotFoundException("There are no category with id " + id + " to delete");
        }
        List<Product> products =
                productRepository.findByCategoryNameAndRemovedFalse(category.get().getName());
        for (Product product : products) {
            removeCategoryFromProduct(product.getId());
        }

        categoryRepository.deleteById(id);
    }

}





