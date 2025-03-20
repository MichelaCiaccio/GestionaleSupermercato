package com.example.supermarket.controller;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping(path = "/product/")
    public List<Stock> findByProductName(@RequestParam String productName) {
        return stockService.findByProductName(productName);
    }

    @GetMapping(path = "/supplier/")
    public List<Stock> findBySupplierName(@RequestParam String supplierName) {
        return stockService.findBySupplierName(supplierName);
    }

    @GetMapping(path = "/quantityGreater/")
    public List<Stock> findByQuantityGreaterThan(@RequestParam int quantity) {
        return stockService.findByQuantityGreaterThan(quantity);
    }

    @GetMapping(path = "/quantityLess/")
    public List<Stock> findByQuantityLessThan(@RequestParam int quantity) {
        return stockService.findByQuantityLessThan(quantity);
    }

    @GetMapping(path = "/deliveryDate/")
    public List<Stock> findByDeliveryDate(@RequestParam LocalDate deliveryDate) {
        return stockService.findByDeliveryDate(deliveryDate);
    }

    @GetMapping(path = "/expirationDate/")
    public List<Stock> findByExpirationDate(@RequestParam LocalDate expirationDate) {
        return stockService.findByExpirationDate(expirationDate);
    }

    @GetMapping(path = "/deliveryDatePeriod/")
    public List<Stock> findByExpirationDateBetween(@RequestParam LocalDate expirationDateStart,
                                                   LocalDate expirationDateEnd) {
        return stockService.findByExpirationDateBetween(expirationDateStart, expirationDateEnd);
    }

    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<String> deleteAll() {
        stockService.deleteALl();
        return ResponseEntity.ok("All stocks have been deleted");
    }
}
