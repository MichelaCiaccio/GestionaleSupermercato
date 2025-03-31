package com.example.supermarket.DTO;

import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SaleDTO {

    private Integer id;
    private double totalPrice;
    private double discountPrice;
    private LocalDateTime saleDate;
    private List<ProductSaleDTO> productSales;
    private Discount discount;
    private Deal deal;

}
