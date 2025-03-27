package com.example.supermarket.controller;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stocks")
public class StockController {

    @Autowired
    private StockService stockServ;

    @GetMapping("/all")
    public List<Stock> getAllStocks() {
        return stockServ.findAll();
    }

    @GetMapping("")
    public Stock getProductSupplierStock(@RequestParam String productName,
                                         @RequestParam String supplierName) {
        return stockServ.findStockByProductAndSupplier(productName, supplierName);
    }

    @PutMapping("/addQuantity")
    public ResponseEntity<String> addQuantity(@RequestParam String productName,
                                              @RequestParam String supplierName,
                                              @RequestParam int quantity) {
        stockServ.addStockQuantity(productName, supplierName, quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Quantity added successfully");
    }

    @PutMapping("/subQuantity")
    public ResponseEntity<String> subQuantity(@RequestParam String productName,
                                              @RequestParam String supplierName,
                                              @RequestParam int quantity) {
        stockServ.subStockQuantity(productName, supplierName, quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Quantity reduced successfully");
    }
}
