package com.example.supermarket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.StockRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepo;

    @Mock
    private StockService stockServ;

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
        verify(stockRepo, times(0)).findById(id);
        assertThrows(EntityNotFoundException.class, () -> stockRepo.findById(id));

    }

    @Test
    void testFindAll() {

        // GIVEN
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 2, 15), product, supplier),
                new Stock(2, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 2, 15), product, supplier));

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
                new Stock(1, 36, deliveryDate, LocalDate.of(2026, 2, 15), product, supplier),
                new Stock(2, 36, deliveryDate, LocalDate.of(2026, 2, 15), product, supplier));

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
                new Stock(1, 36, expirationDate, LocalDate.of(2026, 2, 15), product, supplier),
                new Stock(2, 36, expirationDate, LocalDate.of(2026, 2, 15), product, supplier));

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
                new Stock(1, 36, expirationDateStart, LocalDate.of(2026, 2, 15), product, supplier),
                new Stock(2, 36, expirationDateEnd, LocalDate.of(2026, 2, 15), product, supplier));

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

        // GIVEN
        String productName = "Prodotto";
        Product product = new Product(1, productName, new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findByProductName(productName, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findByProductName(productName, null);

        // VERIFY
        verify(stockRepo, times(1)).findByProductName(productName, null);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
        assertNotNull(ret);
    }

    @Test
    void testFindByProductNameException() {

        // GIVEN
        String productName = "Prodotto";

        // WHEN
        when(stockRepo.findByProductName(productName, null))
                .thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findByProductName(productName, null);
        assertThrows(EntityNotFoundException.class,
                () -> stockRepo.findByProductName(productName, null));

    }

    @Test
    void testFindByQuantityGreaterThan() {

        // GIVEN
        int quantity = 36;
        int greaterQuantity = 40;
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, greaterQuantity, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, greaterQuantity, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product,
                        supplier));

        // WHEN
        when(stockRepo.findByQuantityGreaterThan(quantity, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findByQuantityGreaterThan(quantity, null);

        // VERIFY
        verify(stockRepo, times(1)).findByQuantityGreaterThan(quantity, null);
        assertNotNull(ret);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());

    }

    @Test
    void testFindByQuantityGreaterThanException() {

        // GIVEN
        int quantity = 36;

        // WHEN
        when(stockRepo.findByQuantityGreaterThan(quantity, null))
                .thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findByQuantityGreaterThan(quantity, null);
        assertThrows(EntityNotFoundException.class,
                () -> stockRepo.findByQuantityGreaterThan(quantity, null));
    }

    @Test
    void testFindByQuantityLessThan() {

        // GIVEN
        int quantity = 36;
        int lessQuantity = 25;
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, lessQuantity, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, lessQuantity, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product,
                        supplier));

        // WHEN
        when(stockRepo.findByQuantityLessThan(quantity, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findByQuantityLessThan(quantity, null);

        // VERIFY
        verify(stockRepo, times(1)).findByQuantityLessThan(quantity, null);
        assertNotNull(ret);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
    }

    @Test
    void testFindByQuantityLessThanException() {

        // GIVEN
        int quantity = 36;

        // WHEN
        when(stockRepo.findByQuantityLessThan(quantity, null))
                .thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findByQuantityLessThan(quantity, null);
        assertThrows(EntityNotFoundException.class,
                () -> stockRepo.findByQuantityLessThan(quantity, null));
    }

    @Test
    void testFindBySupplierName() {

        // GIVEN
        String supplierName = "Fornitore";
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, supplierName, "Indirizzo", "3336875889", "test@email.com", null);
        List<Stock> stocks = List.of(
                new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier),
                new Stock(2, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier));

        // WHEN
        when(stockRepo.findBySupplierName(supplierName, null)).thenReturn(stocks);
        List<Stock> ret = stockRepo.findBySupplierName(supplierName, null);

        // VERIFY
        verify(stockRepo, times(1)).findBySupplierName(supplierName, null);
        assertEquals(ret, stocks);
        assertEquals(2, ret.size());
        assertNotNull(ret);
    }

    @Test
    void testFindBySupplierNameException() {

        // GIVEN
        String supplierName = "Fornitore";

        // WHEN
        when(stockRepo.findBySupplierName(supplierName, null))
                .thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findBySupplierName(supplierName, null);
        assertThrows(EntityNotFoundException.class,
                () -> stockRepo.findBySupplierName(supplierName, null));

    }

    @Test
    void testSaveSuccessfully() {

        // GIVEN
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        Stock stock = new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier);

        // WHEN
        when(stockRepo.existsByProductNameAndSupplierNameAndExpirationDate(stock.getProduct().getName(),
                stock.getSupplier().getName(), stock.getExpirationDate()))
                .thenReturn(false);
        Boolean exists = stockRepo.existsByProductNameAndSupplierNameAndExpirationDate(stock.getProduct().getName(),
                stock.getSupplier().getName(), stock.getExpirationDate());
        stockRepo.save(stock);

        // VERIFY
        verify(stockRepo, times(1)).save(stock);
        assertFalse(exists);
    }

    @Test
    void testSaveStockWithNullSupplier() {

        // GIVEN
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Stock stock = new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, null);

        // WHEN
        when(stockRepo.save(stock)).thenThrow(new DataIntegrityViolationException("Stock must have a supplier"));

        // VERIFY
        verify(stockRepo, times(0)).save(stock);
        assertThrows(DataIntegrityViolationException.class,
                () -> stockRepo.save(stock));
    }

    @Test
    void testSaveWithDuplicateStock() {

        // GIVEN
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        Stock stock = new Stock(1, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier);

        // WHEN
        when(stockRepo.existsByProductNameAndSupplierNameAndExpirationDate(stock.getProduct().getName(),
                stock.getSupplier().getName(), stock.getExpirationDate()))
                .thenThrow(new DuplicateKeyException("The stock for " + stock.getProduct().getName() + " from "
                        + stock.getSupplier().getName() + " already existis"));

        // VERIFY
        verify(stockRepo, times(0)).existsByProductNameAndSupplierNameAndExpirationDate(stock.getProduct().getName(),
                stock.getSupplier().getName(), stock.getExpirationDate());
        assertThrows(DuplicateKeyException.class,
                () -> stockRepo.existsByProductNameAndSupplierNameAndExpirationDate(stock.getProduct().getName(),
                        stock.getSupplier().getName(), stock.getExpirationDate()));
    }

    @Test
    void testUpdate() {

        // GIVEN
        int id = 1;
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Product modProduct = new Product(1, "Prodotto modificato", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        Stock stock = new Stock(id, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier);
        Stock modStock = new Stock(id, 36, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), modProduct, supplier);

        // WHEN
        when(stockRepo.findById(id)).thenReturn(Optional.of(stock)).thenReturn(Optional.of(modStock));
        Optional<Stock> existingStock = stockRepo.findById(id);
        stockServ.update(id, modStock);
        Optional<Stock> updatedStock = stockRepo.findById(id);

        // VERIFY
        verify(stockRepo, times(2)).findById(id);
        verify(stockServ, times(1)).update(id, modStock);
        assertNotEquals(existingStock, updatedStock);
        assertNotNull(existingStock);
        assertNotNull(updatedStock);
    }

    @Test
    void testUpdateException() {

        // GIVEN
        int id = 1;

        // WHEN
        when(stockRepo.findById(id)).thenThrow(new EntityNotFoundException());

        // VERIFY
        verify(stockRepo, times(0)).findById(id);
        assertThrows(EntityNotFoundException.class, () -> stockRepo.findById(id));
    }

    @Test
    void testUpdateQuantity() {

        // GIVEN
        int id = 1;
        int quantity = 36;
        int newQuantity = quantity + 30;
        Product product = new Product(1, "Prodotto", new BigDecimal(26), null, null);
        Supplier supplier = new Supplier(1, "Fornitore", "Indirizzo", "3336875889", "test@email.com", null);
        Stock stock = new Stock(id, quantity, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product, supplier);
        Stock modStock = new Stock(id, newQuantity, LocalDate.of(2025, 3, 12), LocalDate.of(2026, 02, 15), product,
                supplier);

        // WHEN
        when(stockRepo.findById(id)).thenReturn(Optional.of(stock)).thenReturn(Optional.of(modStock));
        Optional<Stock> existingStock = stockRepo.findById(id);
        stockServ.updateQuantity(newQuantity, id);
        Optional<Stock> newStock = stockRepo.findById(id);

        // VERIFY
        verify(stockRepo, times(2)).findById(id);
        verify(stockServ, times(1)).updateQuantity(newQuantity, id);
        assertNotNull(existingStock);
        assertNotNull(newStock);
        assertNotEquals(existingStock, newStock);
    }
}
