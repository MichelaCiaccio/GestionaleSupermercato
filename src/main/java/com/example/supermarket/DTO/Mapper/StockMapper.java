package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.StockDTO;
import com.example.supermarket.DTO.StockSummaryDTO;
import com.example.supermarket.entity.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = SupplierMapper.class)
public interface StockMapper {

    StockDTO toStockDTO(Stock stock);

    List<StockDTO> toStockDTOs(List<Stock> stocks);

    StockSummaryDTO toStockSummaryDTO(Stock stock);

    List<StockSummaryDTO> toStockSummaryDTOs(List<Stock> stocks);
}
