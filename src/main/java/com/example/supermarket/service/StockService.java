package com.example.supermarket.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.StockRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks");
        }
        return stocks;
    }

    public List<Stock> findByProductName(String name) {
        List<Stock> stocks = stockRepository.findByProductName(name);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no product with name " + name + " in stock");
        }
        return stocks;
    }

    public List<Stock> findBySupplierName(String name) {
        List<Stock> stocks = stockRepository.findBySupplierName(name);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks from supplier with name " + name);
        }
        return stocks;
    }

    public List<Stock> findByQuantityGreaterThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityGreaterThan(quantity);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity greater than " + quantity);
        }
        return stocks;
    }

    public List<Stock> findByQuantityLessThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityLessThan(quantity);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity less than " + quantity);
        }
        return stocks;
    }

    public List<Stock> findByDeliveryDate(LocalDate deliveryDate) {
        List<Stock> stocks = stockRepository.findByDeliveryDate(deliveryDate);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products in stock arrived on " + deliveryDate);
        }
        return stocks;
    }

    public List<Stock> findByExpirationDate(LocalDate expirationDate) {
        List<Stock> stocks = stockRepository.findByExpirationDate(expirationDate);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(
                    "There are no products in stock with expiration date equals to " + expirationDate);
        }
        return stocks;
    }

    public List<Stock> findByExpirationDateBetween(LocalDate firstDate, LocalDate seconDate) {
        List<Stock> stocks = stockRepository.findByExpirationDateBetween(firstDate, seconDate);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(
                    "There are no products in stock with exipration date betweem " + firstDate + " and " + seconDate);
        }
        return stocks;
    }

    /**
     * Questo metodo recupera lo stock relativo ad un prodotto specifico,
     * recuperandolo tramite id, e ne aggiorna la quantità sommando quella già
     * esistente a quella nuova
     * 
     * @param newStockQuantity
     * @param productId
     */
    public ResponseEntity<String> updateQuantity(int newStockQuantity, int productId) {
        Stock stock = stockRepository.findByProductId(productId);
        stock.setQuantity(stock.getQuantity() + newStockQuantity);
        return ResponseEntity.ok("Quantity for product " + stock.getProduct().getName() + " updated successfully");

    }

    public void save(Stock stock) {
        if (stockRepository.existsByProductNameAndSupplierName(stock.getProduct().getName(),
                stock.getSupplier().getName())) {
            throw new DuplicateKeyException(
                    "The stock for this product already exists. Instead of creating new stock, update the quantity");
        }
        stockRepository.save(stock);
    }

}
