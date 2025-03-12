package com.example.supermarket.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.StockRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    /**
     * The method calls the stockRepository findAll to search for all the stocks
     * if there are no stocks throws an EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @return the stocks found
     */
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll(PageRequest.of(0, 20)).getContent();
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks");
        }
        return stocks;
    }

    /**
     * This method searches for stocks using the name as a parameter
     * if the stocks are empty throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param name the name of the stock
     * @return the stocks found
     */
    public List<Stock> findByProductName(String name) {
        List<Stock> stocks = stockRepository.findByProductName(name, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no product with name " + name + " in stock");
        }
        return stocks;
    }

    /**
     * This method searches for stocks using the name of the supplier as a parameter
     * if the stocks are empty throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param name the name of the supplier
     * @return the stocks found
     */
    public List<Stock> findBySupplierName(String name) {
        List<Stock> stocks = stockRepository.findBySupplierName(name, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks from supplier with name " + name);
        }
        return stocks;
    }

    /**
     * This method searches for stocks using the quantity as a parameter
     * to search for amounts of stocks greater than the given quantity
     * if there are no stocks in quantity greater than the one indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param quantity
     * @return the stocks found
     */
    public List<Stock> findByQuantityGreaterThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityGreaterThan(quantity, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity greater than " + quantity);
        }
        return stocks;
    }

    /**
     * This method searches for stocks using the quantity as a parameter
     * to search for amounts of stocks lower than the given quantity
     * if there are no stocks in quantity lower than the one indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param quantity
     * @return the stocks found
     */
    public List<Stock> findByQuantityLessThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityLessThan(quantity, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity less than " + quantity);
        }
        return stocks;
    }

    /**
     * This method searches for stocks with a deliveryDate corresponding to the one
     * given as the input parameter
     * if there are no stocks corresponding to the filter indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param deliveryDate
     * @return the stocks found
     */
    public List<Stock> findByDeliveryDate(LocalDate deliveryDate) {
        List<Stock> stocks = stockRepository.findByDeliveryDate(deliveryDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products in stock arrived on " + deliveryDate);
        }
        return stocks;
    }

    /**
     * This method searches for stocks with an expirationDate corresponding to the one
     * given as the input parameter
     * if there are no stocks corresponding to the filter indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param expirationDate
     * @return the stocks found
     */
    public List<Stock> findByExpirationDate(LocalDate expirationDate) {
        List<Stock> stocks = stockRepository.findByExpirationDate(expirationDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(
                    "There are no products in stock with expiration date equals to " + expirationDate);
        }
        return stocks;
    }

    /**
     * This method searches for stocks with an expirationDate between the ones
     * provided as the input parameter
     * if there are no stocks corresponding to the filter indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     * @param firstDate
     * @param seconDate
     * @return the stocks found
     */
    public List<Stock> findByExpirationDateBetween(LocalDate firstDate, LocalDate seconDate) {
        List<Stock> stocks = stockRepository.findByExpirationDateBetween(firstDate, seconDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(
                    "There are no products in stock with exipration date betweem " + firstDate + " and " + seconDate);
        }
        return stocks;
    }

    /**
     * This method searches and updates the stock of a specific product
     * the method calls the stockRepository.findById to search for the specific stock
     * then modifies it by adding the new quantity indicated as an input
     * @param newStockQuantity the quantity to add to the stock
     * @param productId the id of the product
     */
    public ResponseEntity<String> updateQuantity(int newStockQuantity, int productId) {
        Stock stock = stockRepository.findByProductId(productId);
        stock.setQuantity(stock.getQuantity() + newStockQuantity);
        return ResponseEntity.ok("Quantity for product " + stock.getProduct().getName() + " updated successfully");

    }

    /**
     * This method creates a new stock of a product.
     * Checks if the stock already exists and if it does, throws a DuplicateKeyException,
     * otherwise calls the stockRepository.save method to create the new stock.
     * @param stock
     */
    public void save(Stock stock) {
        if (stockRepository.existsByProductNameAndSupplierName(stock.getProduct().getName(),
                stock.getSupplier().getName())) {
            throw new DuplicateKeyException(
                    "The stock for this product already exists. Instead of creating new stock, update the quantity");
        }
        stockRepository.save(stock);
    }

}
