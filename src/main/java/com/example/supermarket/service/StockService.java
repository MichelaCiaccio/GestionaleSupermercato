package com.example.supermarket.service;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    public Stock findStockByProductAndSupplier(String productName, String supplierName) {

        return stockRepo.findByProduct_NameAndSupplier_Name(productName, supplierName)
                .orElseThrow(() -> new EntityNotFoundException("There no stock for " +
                                                                       "product " + productName + " supplied by " + supplierName));
    }

    public void addStockQuantity(String productName, String supplierName, int modQuantity) {
        Stock stock = stockRepo.findByProduct_NameAndSupplier_Name(productName, supplierName)
                .orElseThrow(() -> new EntityNotFoundException("There no stock for " +
                                                                       "product " + productName + " supplied by " + supplierName));
        stock.setQuantity(modQuantity + stock.getQuantity());
    }

    public void subStockQuantity(String productName, String supplierName, int modQuantity) {
        Stock stock = stockRepo.findByProduct_NameAndSupplier_Name(productName, supplierName)
                .orElseThrow(() -> new EntityNotFoundException("There no stock for " +
                                                                       "product " + productName + " supplied by " + supplierName));
        stock.setQuantity(stock.getQuantity() - modQuantity);
    }
}
