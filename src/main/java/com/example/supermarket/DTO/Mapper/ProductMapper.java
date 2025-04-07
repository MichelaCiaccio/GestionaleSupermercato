package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.ProductDTO;
import com.example.supermarket.DTO.ProductSummaryDTO;
import com.example.supermarket.DTO.StockDTO;
import com.example.supermarket.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    public ProductSummaryDTO toProductSummaryDTO(Product product) {
        return new ProductSummaryDTO(product.getId(),
                                     product.getName());
    }

    public ProductDTO toProductDTO(Product product) {


        List<StockDTO> stockDTOS =
                product.getStocks().stream().map(stock -> new StockDTO(stock.getQuantity(),
                                                                       stock.getDeliveryDate(),
                                                                       stock.getExpirationDate(),
                                                                       supplierMapper.toSupperDTO(stock.getSupplier()))).toList();

        return new ProductDTO(product.getName(), product.getSellingPrice(),
                              product.getDiscountedSellingPrice(), product.getCategory(),
                              stockDTOS, product.getDiscount());
    }
}
