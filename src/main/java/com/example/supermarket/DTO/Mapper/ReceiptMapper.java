package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.ReceiptDTO;
import com.example.supermarket.DTO.SaleDTO;
import com.example.supermarket.entity.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    @Autowired
    private SaleMapper saleMapper;

    public ReceiptDTO toReceiptDTO(Receipt receipt) {
        SaleDTO saleDTO = saleMapper.toSaleDto(receipt.getSale());

        return new ReceiptDTO(receipt.getReceiptCode(), saleDTO);
    }
}
