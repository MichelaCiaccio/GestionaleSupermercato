package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.DealDTO;
import com.example.supermarket.entity.Deal;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {

    public DealDTO toDealDTO(Deal deal) {
        return new DealDTO(deal.getName(), deal.getDealType(), deal.getEndDate(), deal.isActive(),
                           deal.getCategories());
    }

}
