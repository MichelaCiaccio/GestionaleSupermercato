package com.example.supermarket.service;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    /**
     * This method searches for a stock with a specified product and supplier.
     * Checks if the stock exists and return it.
     * * Otherwise, throw an EntityNotFoundException.
     *
     * @param productName  The name of the product in the stock
     * @param supplierName The name of the supplier in the stock
     * @return The found stock
     */
    public Stock findStockByProductAndSupplier(String productName, String supplierName) {

        return stockRepo.findByProduct_NameAndSupplier_Name(productName, supplierName)
                .orElseThrow(() -> new EntityNotFoundException("There no stock for " +
                                                                       "product " + productName + " supplied by " + supplierName));
    }

    /**
     * This method increases the quantity in a specified stock.
     * It searches for a stock with a specified id.
     * Check if the stock exists if it doesn't throw an EntityNotFoundException.
     * If it does, set the new quantity
     *
     * @param id          The id of the product in the stock
     * @param modQuantity The quantity to add to the existing stock
     */
    public void addStockQuantity(int id, int modQuantity) {
        Stock stock = stockRepo.findByProduct_Id(id)
                .orElseThrow(() -> new EntityNotFoundException("There no stock for " +
                                                                       "product with id " + id));
        stock.setQuantity(modQuantity + stock.getQuantity());
        stockRepo.save(stock);
    }

    /**
     * This method decreases the quantity in a specified stock.
     * It searches for a stock with a specified id.
     * Check if the stock exists if it doesn't throw an EntityNotFoundException.
     * If it does, set the new quantity
     *
     * @param id          The id of the product in the stock
     * @param modQuantity The quantity to add to the existing stock
     */
    public void subStockQuantity(int id, int modQuantity) {
        Stock stock = stockRepo.findByProduct_Id(id)
                .orElseThrow(() -> new EntityNotFoundException("There no stock for " +
                                                                       "product  with id " + id));
        stock.setQuantity(stock.getQuantity() - modQuantity);
        stockRepo.save(stock);
    }

    /**
     * This method searches for all the stock.
     * Check if any stock exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @return The found stocks
     */
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepo.findAll();
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks");
        }
        return stocks;
    }
}
