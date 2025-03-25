package com.example.supermarket.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StockDTO {

    private int quantity;
    private LocalDate expirationDate;
    private LocalDate deliveryDate;
}
