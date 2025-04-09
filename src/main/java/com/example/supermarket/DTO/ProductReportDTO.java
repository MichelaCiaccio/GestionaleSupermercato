package com.example.supermarket.DTO;

import com.example.supermarket.entity.Category;
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
    private Category category;
}
