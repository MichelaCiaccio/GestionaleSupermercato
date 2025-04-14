package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.*;
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

    @Autowired
    private CategoryMapper categoryMapper;

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

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(product.getCategory());

        return new ProductDTO(product.getName(), product.getSellingPrice(),
                              product.getDiscountedSellingPrice(), categoryDTO,
                              stockDTOS, product.getDiscount());
    }

    public ProductReportDTO toProductReportDTO(ProductDTO productDTO) {
        return new ProductReportDTO(productDTO.getName(), productDTO.getSellingPrice(),
                                    productDTO.getDiscountedSellingPrice(),
                                    productDTO.getCategory());
    }
}
