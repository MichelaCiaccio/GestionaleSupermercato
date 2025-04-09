package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductReportDTO {

    private String name;
    private BigDecimal sellingPrice;
    private BigDecimal discountedSellingPrice;
    private CategoryDTO category;
}
