package com.example.supermarket.DTO.Report;


import com.example.supermarket.DTO.ProductReportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StocksRecord {

    private ProductReportDTO product;
    private LocalDate expirationDate;
    private int quantity;
}
