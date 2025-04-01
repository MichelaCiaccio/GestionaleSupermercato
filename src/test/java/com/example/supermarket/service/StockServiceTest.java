package com.example.supermarket.service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepo;

    @InjectMocks
    private StockService stockServ;

    @Test
    void findStockByProductAndSupplier() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      null, null);
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), product,
                                supplier);

        // When
        when(stockRepo.findByProduct_NameAndSupplier_Name(product.getName(), supplier.getName())).thenReturn(Optional.of(stock));
        Optional<Stock> ret = stockRepo.findByProduct_NameAndSupplier_Name(product.getName(),
                                                                           supplier.getName());

        // Verify
        verify(stockRepo, times(1)).findByProduct_NameAndSupplier_Name(product.getName(),
                                                                       supplier.getName());
        assertNotNull(ret);
        assertEquals(ret.get(), stock);
    }

    @Test
    void findStockByProductAndSupplierException() {

        // When
        when(stockRepo.findByProduct_NameAndSupplier_Name(anyString(), anyString())).thenThrow(new EntityNotFoundException());

        // Verify
        verify(stockRepo, times(0)).findByProduct_NameAndSupplier_Name(anyString(), anyString());
        assertThrows(EntityNotFoundException.class,
                     () -> stockRepo.findByProduct_NameAndSupplier_Name(anyString(), anyString()));
    }

    @Test
    void addStockQuantity() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      null, null);
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), product,
                                supplier);
        int quantity = 10;

        // When
        when(stockRepo.findByProduct_Id(product.getId())).thenReturn(Optional.of(stock));
        stockServ.addStockQuantity(product.getId(), quantity);

        // Verify
        verify(stockRepo, times(1)).findByProduct_Id(product.getId());
        verify(stockRepo, times(1)).save(stock);
        assertEquals(20, stock.getQuantity());


    }

    @Test
    void addStockQuantityException() {

        // When
        when(stockRepo.findByProduct_Id(anyInt())).thenThrow(new EntityNotFoundException());

        // Verify
        verify(stockRepo, times(0)).findByProduct_Id(anyInt());
        assertThrows(EntityNotFoundException.class,
                     () -> stockRepo.findByProduct_Id(anyInt()));
    }

    @Test
    void subStockQuantity() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      null, null);
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), product,
                                supplier);
        int quantity = 10;

        // When
        when(stockRepo.findByProduct_Id(product.getId())).thenReturn(Optional.of(stock));
        stockServ.subStockQuantity(product.getId(), quantity);

        // Verify
        verify(stockRepo, times(1)).findByProduct_Id(product.getId());
        verify(stockRepo, times(1)).save(stock);
        assertEquals(0, stock.getQuantity());
    }

    @Test
    void subStockQuantityException() {

        // When
        when(stockRepo.findByProduct_Id(anyInt())).thenThrow(new EntityNotFoundException());

        // Verify
        verify(stockRepo, times(0)).findByProduct_Id(anyInt());
        assertThrows(EntityNotFoundException.class,
                     () -> stockRepo.findByProduct_Id(anyInt()));
    }


    @Test
    void findAll() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      null, null);
        List<Stock> stocks = List.of(
                new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), product,
                          supplier),
                new Stock(1, 50, LocalDate.now(), LocalDate.now().plusDays(10), product,
                          supplier));

        // When
        when(stockRepo.findAll()).thenReturn(stocks);
        List<Stock> ret = stockRepo.findAll();

        // Verify
        verify(stockRepo, times(1)).findAll();
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
    }

    @Test
    void testFindAllException() {

        // When
        when(stockRepo.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(stockRepo, times(0)).findAll();
        assertThrows(EntityNotFoundException.class,
                     () -> stockRepo.findAll());
    }
}