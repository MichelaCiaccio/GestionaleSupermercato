package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.ReceiptDTO;
import com.example.supermarket.entity.Receipt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = SaleMapper.class)
public interface ReceiptMapper {


    ReceiptDTO toReceiptDTO(Receipt receipt);
}
