package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    
    private BigDecimal totalPrice;
    private BigDecimal discountPrice;
    private LocalDateTime saleDate;
    private List<ProductSaleDTO> productSales;
    private DealDTO deal;

}
