package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.DealDTO;
import com.example.supermarket.entity.Deal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealMapper {

    DealDTO toDealDTO(Deal deal);

}
