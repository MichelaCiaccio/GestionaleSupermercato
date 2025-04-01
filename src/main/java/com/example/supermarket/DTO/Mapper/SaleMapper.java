package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.ProductSaleDTO;
import com.example.supermarket.DTO.SaleDTO;
import com.example.supermarket.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleMapper {

    @Autowired
    private ProductMapper productMapper;

    public SaleDTO toSaleDto(Sale sale) {

        // Mappo ogni ProductSale in ProductSaleDTO
        List<ProductSaleDTO> productSaleDTOList = sale.getProductSales().stream()
                .map(productSale -> {
                    return new ProductSaleDTO(productSale.getQuantity(),
                                              productMapper.toProductSummaryDTO(productSale.getProduct()));
                })
                .toList();

        return new SaleDTO(sale.getId(), sale.getTotalPrice(), sale.getDiscountPrice()
                , sale.getSaleDate(), productSaleDTOList, sale.getDeal());
    }

}
