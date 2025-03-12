package com.example.supermarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.service.StockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping(path = "/all")
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> addNewStock(@Valid @RequestBody Stock stock) {
        try {
            stockService.save(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body("Stock created successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
