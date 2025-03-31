package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductSaleDTO {

    private int quantity;
    private ProductSummaryDTO product;
}
