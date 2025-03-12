package com.example.supermarket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.StockRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepo;

    @Test
    void testDeleteALl() {

        // GIVEN
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findAll()).thenReturn(stocks).thenReturn(null);
        doNothing().when(stockRepo).deleteAll();
        List<Stock> existingStocks = stockRepo.findAll();
        stockRepo.deleteAll();
        List<Stock> deleteStocks = stockRepo.findAll();

        // VERIFY
        verify(stockRepo, times(2)).findAll();
        verify(stockRepo, times(1)).deleteAll();
        assertNull(deleteStocks);
        assertNotEquals(existingStocks, deleteStocks);

    }

    @Test
    void deleteAllException() {

        // WHEN
        when(stockRepo.findAll()).thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, stockRepo::findAll);
    }

    @Test
    void testDeleteByID() {

        // GIVEN
        int id = 1;
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        Stock stock = new Stock(id, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier);

        // WHEN
        when(stockRepo.findById(id)).thenReturn(Optional.of(stock)).thenReturn(Optional.empty());
        doNothing().when(stockRepo).deleteById(id);
        Optional<Stock> existingStock = stockRepo.findById(id);
        stockRepo.deleteById(id);
        Optional<Stock> deleteStock = stockRepo.findById(id);

        // VERIFY
        verify(stockRepo, times(2)).findById(id);
        verify(stockRepo, times(1)).deleteById(id);
        assertNotEquals(existingStock, deleteStock);
        assertTrue(existingStock.isPresent());
        assertNull(deleteStock.orElse(null));
    }

    @Test
    void testDeleteByIdException() {

        // GIVEN
        int id = 1;

        // WHEN
        when(stockRepo.findById(id)).thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findById(null);
        assertThrows(EntityNotFoundException.class, () -> stockRepo.findById(id));

    }

    @Test
    void testFindAll() {

        // GIVEN
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findAll()).thenReturn(stocks).thenReturn(null);
        List<Stock> ret = stockRepo.findAll();

        // VERIFY
        verify(stockRepo, times(1)).findAll();
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
        assertNotNull(ret);

    }

    @Test
    void testFindAllException() {

        // WHEN
        when(stockRepo.findAll()).thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, stockRepo::findAll);

    }

    @Test
    void testFindByDeliveryDate() {
        // GIVEN
        LocalDate deliveryDate = LocalDate.of(2025, 3, 12);
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, deliveryDate, LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, deliveryDate, LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findByDeliveryDate(deliveryDate, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findByDeliveryDate(deliveryDate, null);

        // VERIFY
        verify(stockRepo, times(1)).findByDeliveryDate(deliveryDate, null);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
        assertNotNull(ret);
    }

    @Test
    void findByDeliveryDateException() {

        // GIVEN
        LocalDate deliveryDate = LocalDate.of(2025, 3, 12);

        // WHEN
        when(stockRepo.findByDeliveryDate(deliveryDate, null)).thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findByDeliveryDate(deliveryDate, null);
        assertThrows(EntityNotFoundException.class, () -> stockRepo.findByDeliveryDate(deliveryDate, null));
    }

    @Test
    void testFindByExpirationDate() {

        // GIVEN
        LocalDate expirationDate = LocalDate.of(2025, 3, 12);
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, expirationDate, LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, expirationDate, LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findByExpirationDate(expirationDate, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findByExpirationDate(expirationDate, null);

        // VERIFY
        verify(stockRepo, times(1)).findByExpirationDate(expirationDate, null);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
        assertNotNull(ret);

    }

    @Test
    void findByExpirationDateException() {

        // GIVEN
        LocalDate expirationDate = LocalDate.of(2025, 3, 12);

        // WHEN
        when(stockRepo.findByExpirationDate(expirationDate, null)).thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findByExpirationDate(expirationDate, null);
        assertThrows(EntityNotFoundException.class, () -> stockRepo.findByExpirationDate(expirationDate, null));
    }

    @Test
    void testFindByExpirationDateBetween() {

        // GIVEN
        LocalDate expirationDateStart = LocalDate.of(2025, 3, 12);
        LocalDate expirationDateEnd = LocalDate.of(2026, 3, 12);
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, expirationDateStart, LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, expirationDateEnd, LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findByExpirationDateBetween(expirationDateStart, expirationDateEnd, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findByExpirationDateBetween(expirationDateStart, expirationDateEnd, null);

        // VERIFY
        verify(stockRepo, times(1)).findByExpirationDateBetween(expirationDateStart, expirationDateEnd, null);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
        assertNotNull(ret);
    }

    @Test
    void findByExpirationDateBetweenException() {

        // GIVEN
        LocalDate expirationDateStart = LocalDate.of(2025, 3, 12);
        LocalDate expirationDateEnd = LocalDate.of(2026, 3, 12);

        // WHEN
        when(stockRepo.findByExpirationDateBetween(expirationDateStart, expirationDateEnd, null))
                .thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findByExpirationDateBetween(expirationDateStart, expirationDateEnd, null);
        assertThrows(EntityNotFoundException.class,
                () -> stockRepo.findByExpirationDateBetween(expirationDateStart, expirationDateEnd, null));
    }

    @Test
    void testFindByProductName() {

    }

    @Test
    void testFindByQuantityGreaterThan() {

    }

    @Test
    void testFindByQuantityLessThan() {

    }

    @Test
    void testFindBySupplierName() {

    }

    @Test
    void testSave() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testUpdateQuantity() {

    }
}
