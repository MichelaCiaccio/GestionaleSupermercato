package com.example.supermarket.DTO.Mapper;

import com.example.supermarket.DTO.SupplierDTO;
import com.example.supermarket.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierDTO toSupperDTO(Supplier supplier) {
        return new SupplierDTO(supplier.getName());
    }
}
