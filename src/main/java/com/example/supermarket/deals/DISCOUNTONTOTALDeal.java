package com.example.supermarket.deals;

import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.ProductSale;

import java.math.BigDecimal;
import java.util.List;

public class DISCOUNTONTOTALDeal implements DealStrategy {
    @Override
    public BigDecimal applyDeal(List<ProductSale> productSales, Deal deal) {
        return null;
    }
}
