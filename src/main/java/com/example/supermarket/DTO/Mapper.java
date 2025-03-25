package com.example.supermarket.DTO;

import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public ProductDTO convertToDTO(Product product) {

        String name = product.getName();
        BigDecimal sellingPrice = product.getSellingPrice();
        Set<String> supplierNames =
                product.getSuppliers().stream().map(Supplier::getName).collect(Collectors.toSet());
        Stock stock = product.getStock();
        StockDTO stockDTO = new StockDTO();
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setDeliveryDate(stock.getDeliveryDate());
        stockDTO.setExpirationDate(stock.getExpirationDate());

        return new ProductDTO(name, sellingPrice, supplierNames, stockDTO);
    }
}
