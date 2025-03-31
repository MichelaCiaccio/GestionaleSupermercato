package com.example.supermarket.service;

import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepo;


    @Test
    void findAllSalesSorted() {

        // Given
        ProductSale productSale = new ProductSale();
        List<Sale> sales = List.of(new Sale(1, 100, 100, LocalDateTime.now(), List.of(productSale),
                                            null,
                                            null),
                                   new Sale(1, 100, 100, LocalDateTime.now(),
                                            List.of(productSale), null,
                                            null));

        // When
        when(saleRepo.findAll()).thenReturn(sales);
        List<Sale> ret = saleRepo.findAll();

        // Verify
        verify(saleRepo, times(1)).findAll();
        assertEquals(ret, sales);
        assertNotNull(ret);
        assertEquals(2, ret.size());
    }

    @Test
    void findAllException() {

        // When
        when(saleRepo.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(saleRepo, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, () -> saleRepo.findAll());
    }

    @Test
    void findBySaleDate() {

        // Given
        LocalDateTime saleDate = LocalDateTime.now();
        ProductSale productSale = new ProductSale();
        List<Sale> sales = List.of(new Sale(1, 100, 100, saleDate, List.of(productSale),
                                            null,
                                            null),
                                   new Sale(1, 100, 100, saleDate,
                                            List.of(productSale), null,
                                            null));

        // When
        when(saleRepo.findBySaleDate(saleDate)).thenReturn(sales);
        List<Sale> ret = saleRepo.findBySaleDate(saleDate);

        // Verify
        verify(saleRepo, times(1)).findBySaleDate(saleDate);
        assertEquals(ret, sales);
        assertNotNull(ret);
        assertEquals(2, ret.size());
    }

    @Test
    void findBySaleDateException() {

        // Given
        LocalDateTime saleDate = LocalDateTime.now();

        // When
        when(saleRepo.findBySaleDate(saleDate)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(saleRepo, times(0)).findBySaleDate(saleDate);
        assertThrows(EntityNotFoundException.class, () -> saleRepo.findBySaleDate(saleDate));
    }

    @Test
    void createNewSale() {
    }
}