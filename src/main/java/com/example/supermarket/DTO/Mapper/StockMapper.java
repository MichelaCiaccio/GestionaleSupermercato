package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.StockDTO;
import com.example.supermarket.entity.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = SupplierMapper.class)
public interface StockMapper {

    StockDTO toStockDTO(Stock stock);
}
