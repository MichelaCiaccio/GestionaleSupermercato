package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.ProductSummaryDTO;
import com.example.supermarket.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductSummaryDTO toProductSummaryDTO(Product product) {
        return new ProductSummaryDTO(product.getId(),
                                     product.getName());
    }
}
