package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.SaleDTO;
import com.example.supermarket.entity.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, DealMapper.class})
public interface SaleMapper {

    SaleDTO toSaleDto(Sale sale);

}
