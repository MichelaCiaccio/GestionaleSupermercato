package com.example.supermarket.controller;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stocks")
public class StockController {

    @Autowired
    private StockService stockServ;

    @GetMapping("")
    public Stock getProductSupplierStock(@RequestParam String productName,
                                         @RequestParam String supplierName) {
        return stockServ.findStockByProductAndSupplier(productName, supplierName);
    }
}
