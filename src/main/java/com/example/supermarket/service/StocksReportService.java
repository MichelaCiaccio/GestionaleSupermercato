package com.example.supermarket.service;

import com.example.supermarket.DTO.Mapper.ProductMapper;
import com.example.supermarket.DTO.ProductReportDTO;
import com.example.supermarket.DTO.Report.StocksRecord;
import com.example.supermarket.DTO.Report.StocksReport;
import com.example.supermarket.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StocksReportService {

    @Autowired
    private StockService stockServ;

    @Autowired
    private ProductService productServ;

    @Autowired
    private ProductMapper productMapper;

    /**
     * Calculates the total quantity of all items in the warehouse.
     * This method retrieves all stocks and calculates the total number of units in the warehouse.
     *
     * @return The total number of units in stocks
     */
    private int getTotalUnit() {

        // Recupero tutti gli stock
        List<Stock> stocks = stockServ.findAllStocks();

        // Recupero le quantità e le sommo
        return stocks.stream().mapToInt(Stock::getQuantity).sum();
    }


    /**
     * This method retrieves the list of all stock entries and maps them to StocksRecord.
     *
     * @return a list of StocksRecord
     */
    private List<StocksRecord> getStocksRecords() {

        // Recupero tutti gli stock
        List<Stock> stocks = stockServ.findAllStocks();

        // Recupero le informazioni dallo stock e creo i record per ciascun prodotto
        return stocks.stream().map(stock -> {
            ProductReportDTO product =
                    productMapper.toProductReportDTO(productServ.findById(stock.getProduct().getId()));
            return new StocksRecord(product, stock.getExpirationDate(), stock.getQuantity());
        }).toList();
    }

    /**
     * Generates a stock report containing the list of all stock records and the total quantity
     * of units in stock.
     *
     * @return StocksReport
     */
    public StocksReport generateStocksReport() {
        List<StocksRecord> stocksRecords = this.getStocksRecords();
        int totalUnit = this.getTotalUnit();

        return new StocksReport(LocalDate.now(), stocksRecords, totalUnit);
    }
}
