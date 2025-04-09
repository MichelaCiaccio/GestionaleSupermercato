package com.example.supermarket.DTO.Report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StocksReport {

    private LocalDate generatedAt;
    private List<StocksRecord> stockRecords;
    private int totalUnit;

}
