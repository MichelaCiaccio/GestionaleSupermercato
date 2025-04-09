package com.example.supermarket.controller;


import com.example.supermarket.DTO.SalesReport;
import com.example.supermarket.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/sales-report")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportServ;

    @GetMapping("")
    public ResponseEntity<SalesReport> getSalesReport(@RequestParam LocalDate startDate,
                                                      @RequestParam LocalDate endDate) {

        SalesReport salesReport = salesReportServ.generateReport(startDate, endDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(salesReport);
    }
}
