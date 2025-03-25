package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {

    private String name;
    private BigDecimal sellingPrice;
    private Set<String> supplierNames;
    private StockDTO stock;
}
