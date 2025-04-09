package com.example.supermarket.service;

import com.example.supermarket.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksReportService {

    @Autowired
    private StockService stockServ;

    /**
     * Calculates the total quantity of all items in the warehouse.
     * This method retrieves all stocks and calculates the total number of units in the warehouse.
     *
     * @return The total number of units in stocks
     */
    public int getTotalUnit() {

        // Recupero tutti gli stock
        List<Stock> stocks = stockServ.findAll();

        // Recupero le quantità e le sommo
        return stocks.stream().mapToInt(Stock::getQuantity).sum();
    }
}
