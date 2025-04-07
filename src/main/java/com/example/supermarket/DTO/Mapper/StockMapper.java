package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.StockDTO;
import com.example.supermarket.DTO.SupplierDTO;
import com.example.supermarket.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    @Autowired
    private SupplierMapper supplierMapper;

    public StockDTO toStockDTO(Stock stock) {
        SupplierDTO supplierDTO = supplierMapper.toSupperDTO(stock.getSupplier());
        return new StockDTO(stock.getQuantity(), stock.getDeliveryDate(),
                            stock.getExpirationDate(), supplierDTO);
    }
}
