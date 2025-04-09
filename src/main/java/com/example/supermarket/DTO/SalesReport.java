package com.example.supermarket.DTO;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesReport {

    private LocalDate startDate;
    private LocalDate endDate;
    private int totalQuantitySold;
    private BigDecimal totalAmountSold;
    private List<ProductDTO> bestSellingProducts;
    private List<Category> bestSellingCategories;
    private List<Discount> bestSellingDiscounts;
}
