package com.example.supermarket.service;

import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void createNewSale() {
    }
}