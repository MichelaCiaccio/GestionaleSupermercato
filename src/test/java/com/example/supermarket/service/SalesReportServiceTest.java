package com.example.supermarket.service;

import com.example.supermarket.entity.*;
import com.example.supermarket.repo.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalesReportServiceTest {

    @Mock
    private SaleRepository saleRepo;

    @InjectMocks
    private SalesReportService salesReportServ;

    @Mock
    private SaleService saleServ;


    @Test
    void getTotalProductSoldBetween() {
        // Given
        List<ProductSale> productSales = List.of(new ProductSale(1, 10, null, null),
                                                 new ProductSale(2, 20, null, null));
        Sale sale = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                             LocalDateTime.now(),
                             productSales,
                             null);
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // When
        when(saleServ.findBetweenDate(startDate, endDate)).thenReturn(List.of(sale));
        int totalQuantity = salesReportServ.getTotalProductSoldBetween(startDate, endDate);

        // Verify
        verify(saleServ, times(1)).findBetweenDate(any(LocalDate.class),
                                                   any(LocalDate.class));
        assertEquals(30, totalQuantity);

    }

    @Test
    void getTotalSalesAmountBetween() {

        // Given
        List<ProductSale> productSales = List.of(new ProductSale(1, 10, null, null),
                                                 new ProductSale(2, 20, null, null));
        Sale sale = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                             LocalDateTime.now(),
                             productSales,
                             null);
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // When
        when(saleServ.findBetweenDate(startDate, endDate)).thenReturn(List.of(sale));
        BigDecimal totalAmount = salesReportServ.getTotalAmountSoldBetween(startDate, endDate);

        // Verify
        verify(saleServ, times(1)).findBetweenDate(any(LocalDate.class),
                                                   any(LocalDate.class));
        assertEquals(BigDecimal.valueOf(100), totalAmount);
    }

    @Test
    void getBestSellingProducts() {

        // Given
        Product product1 = new Product(1, "Product 1", BigDecimal.valueOf(10),
                                       BigDecimal.valueOf(10), false, null,
                                       null,
                                       null);
        Product product2 = new Product(2, "Product 2", BigDecimal.valueOf(20),
                                       BigDecimal.valueOf(20), false, null,
                                       null,
                                       null);
        Product product3 = new Product(3, "Product 3", BigDecimal.valueOf(30),
                                       BigDecimal.valueOf(30), false, null,
                                       null,
                                       null);

        ProductSale productSale1 = new ProductSale(1, 5, product1, null);
        ProductSale productSale2 = new ProductSale(2, 15, product2, null);
        ProductSale productSale3 = new ProductSale(3, 25, product3, null);

        Sale sale1 = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                              LocalDateTime.now(), List.of(productSale1, productSale2), null);
        Sale sale2 = new Sale(2, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                              LocalDateTime.now(), List.of(productSale3), null);

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // When
        when(saleServ.findBetweenDate(startDate, endDate)).thenReturn(List.of(sale1, sale2));
        List<Product> bestSellingProducts =
                salesReportServ.getBestSelling(startDate, endDate, product -> product);

        // Verify
        verify(saleServ, times(1)).findBetweenDate(any(LocalDate.class),
                                                   any(LocalDate.class));
        assertEquals(3, bestSellingProducts.size()); // Verifica che siano 3 prodotti
        assertEquals(product3, bestSellingProducts.get(0)); // Il prodotto più venduto
        assertEquals(product2, bestSellingProducts.get(1)); // Il secondo più venduto
        assertEquals(product1, bestSellingProducts.get(2)); // Il terzo più venduto

    }

    @Test
    void getBestSellingCategories() {

        // Given
        Category category1 = new Category(1, "Category1", null);
        Category category2 = new Category(2, "Category2", null);
        Category category3 = new Category(3, "Category3", null);
        Product product1 = new Product(1, "Product 1", BigDecimal.valueOf(10),
                                       BigDecimal.valueOf(10), false, category1, null, null);
        Product product2 = new Product(2, "Product 2", BigDecimal.valueOf(20),
                                       BigDecimal.valueOf(20), false, category2, null, null);
        Product product3 = new Product(3, "Product 3", BigDecimal.valueOf(30),
                                       BigDecimal.valueOf(30), false, category3, null, null);

        ProductSale productSale1 = new ProductSale(1, 5, product1, null);
        ProductSale productSale2 = new ProductSale(2, 15, product2, null);
        ProductSale productSale3 = new ProductSale(3, 25, product3, null);

        Sale sale1 = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                              LocalDateTime.now(), List.of(productSale1, productSale2), null);
        Sale sale2 = new Sale(2, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                              LocalDateTime.now(), List.of(productSale3), null);

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // When
        when(saleServ.findBetweenDate(startDate, endDate)).thenReturn(List.of(sale1, sale2));
        List<Category> bestSellingCategories =
                salesReportServ.getBestSelling(startDate, endDate, Product::getCategory);

        // Verify
        verify(saleServ, times(1)).findBetweenDate(any(LocalDate.class),
                                                   any(LocalDate.class));
        assertEquals(3, bestSellingCategories.size()); // Verifica che siano 3 prodotti
        assertEquals(category3, bestSellingCategories.get(0)); // Il prodotto più venduto
        assertEquals(category2, bestSellingCategories.get(1)); // Il secondo più venduto
        assertEquals(category1, bestSellingCategories.get(2)); // Il terzo più venduto
    }

    @Test
    void getBestSellingDiscount() {

        // Given
        Discount discount1 = new Discount(1, "Discount 1", 10, LocalDate.now(), true);
        Discount discount2 = new Discount(2, "Discount 2", 20, LocalDate.now(), true);
        Discount discount3 = new Discount(3, "Discount 3", 30, LocalDate.now(), true);

        Product product1 = new Product(1, "Product 1", BigDecimal.valueOf(10),
                                       BigDecimal.valueOf(10), false, null,
                                       null,
                                       discount1);
        Product product2 = new Product(2, "Product 2", BigDecimal.valueOf(20),
                                       BigDecimal.valueOf(20), false, null,
                                       null,
                                       discount2);
        Product product3 = new Product(3, "Product 3", BigDecimal.valueOf(30),
                                       BigDecimal.valueOf(30), false, null,
                                       null,
                                       discount3);

        ProductSale productSale1 = new ProductSale(1, 5, product1, null);
        ProductSale productSale2 = new ProductSale(2, 15, product2, null);
        ProductSale productSale3 = new ProductSale(3, 25, product3, null);

        Sale sale1 = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                              LocalDateTime.now(), List.of(productSale1, productSale2), null);
        Sale sale2 = new Sale(2, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                              LocalDateTime.now(), List.of(productSale3), null);

        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        // When
        when(saleServ.findBetweenDate(startDate, endDate)).thenReturn(List.of(sale1, sale2));
        List<Discount> bestSellingDeals =
                salesReportServ.getBestSelling(startDate, endDate, Product::getDiscount);

        // Verify
        verify(saleServ, times(1)).findBetweenDate(any(LocalDate.class),
                                                   any(LocalDate.class));
        assertEquals(3, bestSellingDeals.size()); // Verifica che siano 3 prodotti
        assertEquals(discount3, bestSellingDeals.get(0)); // Il prodotto più venduto
        assertEquals(discount2, bestSellingDeals.get(1)); // Il secondo più venduto
        assertEquals(discount1, bestSellingDeals.get(2)); // Il terzo più venduto

    }
}