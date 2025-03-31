package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductSummaryDTO {

    private Integer id;
    private String name;
}
