package com.example.supermarket.DTO;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {

    private String name;
    private BigDecimal sellingPrice;
    private BigDecimal discountedSellingPrice;
    private Category category;
    private List<StockDTO> stockDTOS;
    private Discount discount;

}
