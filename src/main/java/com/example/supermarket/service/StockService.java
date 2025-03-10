package com.example.supermarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
            throw new EntityNotFoundException("Thera are no stocks");
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
}
