package com.example.supermarket.service;

import com.example.supermarket.DTO.Mapper.ProductMapper;
import com.example.supermarket.DTO.ProductDTO;
import com.example.supermarket.DTO.SalesReport;
import com.example.supermarket.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class SalesReportService {

    @Autowired
    private SaleService saleServ;

    @Autowired
    private ProductMapper productMapper;


    /**
     * Creates a map of keys (e.g., Product or Category) and their corresponding sales quantities.
     * The key is extracted from the product of each sale using the provided keyExtractor.
     *
     * @param targetSale   A list of sales to process
     * @param keyExtractor A function to extract the key from each sale
     * @param <T>          The type of the key
     * @return A map of keys and their total sales quantities
     */
    private static <T> Map<T, Integer> getSalesCountMap(List<Sale> targetSale,
                                                        Function<Product, T> keyExtractor) {
        Map<T, Integer> salesCountMap = new HashMap<>();

        // Recupero prodotto o categoria e la quantità associata
        for (Sale sale : targetSale) {
            for (ProductSale productSale : sale.getProductSales()) {

                // Estraggo il prodotto o la categoria
                T key = keyExtractor.apply(productSale.getProduct());
                int quantity = productSale.getQuantity();

                // Inserisco nella mappa e sommo le quantità
                salesCountMap.put(key, salesCountMap.getOrDefault(key, 0) + quantity);
            }
        }
        return salesCountMap;
    }

    /**
     * This method retrieves the total quantity of products sold between the specified start and
     * end dates.
     * It first retrieves the sales within the given date range and calculates the total quantity
     * of all the products sold.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return The total quantity of products sold
     */
    public int getTotalProductSoldBetween(LocalDate startDate, LocalDate endDate) {
        List<Sale> targetSale = saleServ.findBetweenDate(startDate, endDate);
        List<ProductSale> productSales = targetSale.stream()
                .flatMap(sale -> sale.getProductSales().stream()).toList();
        return productSales.stream().mapToInt(ProductSale::getQuantity).sum();
    }

    /**
     * This method calculates the total sales amount between the specified start and end dates.
     * It retrieves the sales within the given date range
     * and sums up the prices of each sale.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return The total sales amount
     */
    public BigDecimal getTotalAmountSoldBetween(LocalDate startDate, LocalDate endDate) {
        List<Sale> targetSale = saleServ.findBetweenDate(startDate, endDate);
        return targetSale.stream().map(Sale::getDiscountPrice).reduce(BigDecimal.ZERO,
                                                                      BigDecimal::add);
    }


    /**
     * This method retrieves the top-selling items (either products, categories or discount) within
     * the
     * specified date range.
     * The method uses the provided key extractor function
     * to determine the items.
     * It returns a list of the top-selling items sorted by the quantity sold in descending order.
     *
     * @param startDate    The start date of the sales period.
     * @param endDate      The end date of the sales period.
     * @param keyExtractor A function that extracts the key from the product in each sale.
     * @param <T>          The type of the key (e.g., Product or Category).
     * @return A list of the top-selling items, ordered by the quantity sold in descending order.
     */
    public <T> List<T> getBestSelling(LocalDate startDate, LocalDate endDate,
                                      Function<Product, T> keyExtractor) {

        // Recupero le vendite
        List<Sale> targetSale = saleServ.findBetweenDate(startDate, endDate);

        // Recupero una mappa di prodotti-quantità
        Map<T, Integer> categorySalesCount = getSalesCountMap(targetSale,
                                                              keyExtractor);

        // Confronto le quantità di prodotto ed estraggo i primi 3
        return categorySalesCount.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    public SalesReport generateReport(LocalDate startDate, LocalDate endDate) {

        SalesReport salesReport = new SalesReport();
        salesReport.setStartDate(startDate);
        salesReport.setEndDate(endDate);

        int totalQuantitySold = this.getTotalProductSoldBetween(startDate, endDate);
        salesReport.setTotalQuantitySold(totalQuantitySold);

        BigDecimal totalAmountSold = this.getTotalAmountSoldBetween(startDate, endDate);
        salesReport.setTotalAmountSold(totalAmountSold);

        List<ProductDTO> bestSellingProducts = this.getBestSelling(startDate, endDate,
                                                                   product -> product).stream().map(productMapper::toProductDTO).toList();
        salesReport.setBestSellingProducts(bestSellingProducts);

        List<Category> bestSellingCategories = this.getBestSelling(startDate, endDate,
                                                                   Product::getCategory);
        salesReport.setBestSellingCategories(bestSellingCategories);

        List<Discount> bestSellingDiscounts = this.getBestSelling(startDate, endDate,
                                                                  Product::getDiscount);
        salesReport.setBestSellingDiscounts(bestSellingDiscounts);

        return salesReport;

    }
}
