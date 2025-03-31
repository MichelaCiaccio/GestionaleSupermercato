package com.example.supermarket.service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepo;

    @Mock
    private ReceiptService receiptServ;

    @InjectMocks
    private SaleService saleServ;

    @Mock
    private StockService stockServ;


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
    void findByProduct() {

        // Given
        String productName = "Prodotto";
        ProductSale productSale = new ProductSale();
        List<Sale> sales = List.of(new Sale(1, 100, 100, LocalDateTime.now(), List.of(productSale),
                                            null,
                                            null),
                                   new Sale(1, 100, 100, LocalDateTime.now(),
                                            List.of(productSale), null,
                                            null));

        // When
        when(saleRepo.findByProductSales_Product_Name(productName)).thenReturn(sales);
        List<Sale> ret = saleRepo.findByProductSales_Product_Name(productName);

        // Verify
        verify(saleRepo, times(1)).findByProductSales_Product_Name(productName);
        assertEquals(ret, sales);
        assertNotNull(ret);
        assertEquals(2, ret.size());
    }

    @Test
    void findByProductException() {

        // Given
        String productName = "Prodotto";

        // When
        when(saleRepo.findByProductSales_Product_Name(productName)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(saleRepo, times(0)).findByProductSales_Product_Name(productName);
        assertThrows(EntityNotFoundException.class,
                     () -> saleRepo.findByProductSales_Product_Name(productName));

    }

    @Test
    void createNewSale() {

        // Given
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category, null);
        ProductSale productSale = new ProductSale(1, 50, product, null);
        Sale sale = new Sale(1, 100, 100, LocalDateTime.now(), List.of(productSale), null, null);


        // When
        when(saleRepo.save(sale)).thenReturn(sale);
        saleServ.createNewSale(sale);

        // Verify
        verify(saleRepo, times(1)).save(sale);
        verify(receiptServ, times(1)).createNewReceipt(sale);
        assertDoesNotThrow(() -> saleServ.createNewSale(sale));
    }

    @Test
    void createNewSaleRuntimeException() {

        // Given
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category, null);
        ProductSale productSale = new ProductSale(1, 50, product, null);
        Sale sale = new Sale(1, 100, 100, LocalDateTime.now(), List.of(productSale), null, null);

        // When
        when(saleRepo.save(any(Sale.class))).thenThrow(new RuntimeException("Errore durante il " +
                                                                                    "salvataggio"));

        // Verify
        verify(receiptServ, never()).createNewReceipt(any());
        assertThrows(RuntimeException.class, () -> saleServ.createNewSale(sale));
    }

    @Test
    void createNewSaleEntityNotFoundException() {
        // Given
        Category category = new Category(1, "Food");
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category, null);
        ProductSale productSale = new ProductSale(1, 50, product, null);
        Sale sale = new Sale(1, 100, 100, LocalDateTime.now(), List.of(productSale), null, null);

        // When
        when(saleRepo.save(any(Sale.class))).thenThrow(new EntityNotFoundException("Sale not found"));

        // Then
        assertThrows(EntityNotFoundException.class, () -> saleServ.createNewSale(sale));

        verify(saleRepo, times(1)).save(any());
        verify(receiptServ, never()).createNewReceipt(any());
        verify(stockServ, times(1)).subStockQuantity(product.getId(), productSale.getQuantity());
    }

}
