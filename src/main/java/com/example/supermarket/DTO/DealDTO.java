package com.example.supermarket.DTO;

import com.example.supermarket.entity.enums.DealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DealDTO {

    private String name;
    private DealType dealType;
    private LocalDate endDate;
    private boolean active;
}
