package com.example.supermarket.DTO;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.enums.DealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DealDTO {

    private String name;
    private DealType dealType;
    private LocalDate endDate;
    private boolean active;
    private List<Category> categories;
}
