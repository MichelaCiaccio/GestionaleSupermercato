package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StockSummaryDTO {

    private int quantity;
    private LocalDate deliveryDate;
    private LocalDate expirationDate;
    private ProductDTO product;
    private SupplierDTO supplier;

}
