package com.example.supermarket.controller;

import com.example.supermarket.DTO.Report.StocksReport;
import com.example.supermarket.service.StocksReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stock-report")
public class StockReportController {

    @Autowired
    private StocksReportService stocksReportServ;

    @GetMapping("")
    public ResponseEntity<StocksReport> getStocksReport() {
        StocksReport stocksReport = stocksReportServ.generateStocksReport();
        return ResponseEntity.ok(stocksReport);
    }
}
