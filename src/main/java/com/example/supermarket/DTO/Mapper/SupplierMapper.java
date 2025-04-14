package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.SupplierDTO;
import com.example.supermarket.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    
    SupplierDTO toSupplierDTO(Supplier supplier);
}
