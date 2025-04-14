package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.ProductDTO;
import com.example.supermarket.DTO.ProductReportDTO;
import com.example.supermarket.DTO.ProductSummaryDTO;
import com.example.supermarket.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StockMapper.class, SupplierMapper.class,
        CategoryMapper.class})
public interface ProductMapper {


    ProductSummaryDTO toProductSummaryDTO(Product product);

    ProductDTO toProductDTO(Product product);

    ProductReportDTO toProductReportDTO(ProductDTO productDTO);
}
