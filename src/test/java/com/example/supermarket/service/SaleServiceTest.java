package com.example.supermarket.service;

import com.example.supermarket.entity.*;
import com.example.supermarket.repo.ProductRepository;
import com.example.supermarket.repo.ReceiptRepository;
import com.example.supermarket.repo.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepo;

    @Mock
    private ReceiptService receiptServ;

    @Mock
    private StockService stockServ;

    @Mock
    private ReceiptRepository receiptRepo;

    @Mock
    private ProductRepository productRepo;


    @InjectMocks
    private SaleService saleServ;


    @Test
    void findAllSalesSorted() {

        // Given
        ProductSale productSale = new ProductSale();
        List<Sale> sales = List.of(new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(),
                                            List.of(productSale),
                                            null),
                                   new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(),
                                            List.of(productSale),
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
        List<Sale> sales = List.of(new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            saleDate, List.of(productSale),
                                            null),
                                   new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            saleDate,
                                            List.of(productSale),
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
        List<Sale> sales = List.of(new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(), List.of(productSale),

                                            null),
                                   new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(),
                                            List.of(productSale),
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
        Category category = new Category(1, "Food", null);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), null, false, category,
                                      null,
                                      null);
        ProductSale productSale = new ProductSale(1, 50, product, null);
        Sale sale = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                             LocalDateTime.now(), List.of(productSale), null);


        // When
        when(saleRepo.save(sale)).thenReturn(sale);
        when(productRepo.findByIdAndRemovedFalse(1)).thenReturn(Optional.of(product));
        doNothing().when(receiptServ).createNewReceipt(sale);
        saleServ.createNewSale(sale);

        // Verify
        verify(saleRepo, times(1)).save(sale);
        verify(receiptServ, times(1)).createNewReceipt(sale);
        assertDoesNotThrow(() -> saleServ.createNewSale(sale));
    }

    @Test
    void createNewSaleRuntimeException() {

        // Given
        Category category = new Category(1, "Food", null);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), null, false,
                                      category,
                                      null,
                                      null);
        ProductSale productSale = new ProductSale(1, 50, product, null);
        Sale sale = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                             LocalDateTime.now(), List.of(productSale), null);

        // When
        when(productRepo.findByIdAndRemovedFalse(1)).thenReturn(Optional.of(product));
        when(saleRepo.save(any(Sale.class)))
                .thenThrow(new RuntimeException("Errore durante il salvataggio"));


        // Verify
        assertThrows(RuntimeException.class, () -> saleServ.createNewSale(sale));
        verify(saleRepo, times(1)).save(any(Sale.class));

    }

    @Test
    void createNewSaleEntityNotFoundException() {
        // Given
        Category category = new Category(1, "Food", null);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), null, false, category,
                                      null, null);
        ProductSale productSale = new ProductSale(1, 50, product, null);
        Sale sale = new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                             LocalDateTime.now(), List.of(productSale), null);

        // When
        when(productRepo.findByIdAndRemovedFalse(1)).thenReturn(Optional.empty());

        // Verify
        assertThrows(EntityNotFoundException.class, () -> saleServ.createNewSale(sale));
        verify(saleRepo, never()).save(any(Sale.class));
    }

    @Test
    public void deleteAll() {

        // Given
        ProductSale productSale = new ProductSale();
        List<Sale> sales = List.of(new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(), List.of(productSale),

                                            null),
                                   new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(),
                                            List.of(productSale),
                                            null));

        // When
        when(saleRepo.findAll()).thenReturn(sales).thenReturn(null);
        doNothing().when(saleRepo).deleteAll();
        List<Sale> existingSales = saleRepo.findAll();
        saleRepo.deleteAll();
        List<Sale> deletedSales = saleRepo.findAll();


        //Verify
        verify(saleRepo, times(1)).deleteAll();
        assertNotNull(existingSales);
        assertNull(deletedSales);
    }

    @Test
    public void deleteAllException() {

        // When
        when(saleRepo.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(saleRepo, times(0)).findAll();
        verify(saleRepo, never()).deleteAll();
        assertThrows(EntityNotFoundException.class, () -> saleRepo.findAll());

    }

    @Test
    public void deleteById() {

        // Given
        int id = 1;
        ProductSale productSale = new ProductSale();
        Sale sale = new Sale(id, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                             LocalDateTime.now(), List.of(productSale),
                             null);
        Receipt receipt = new Receipt(id, "ABCDGUIJ", sale);


        // When
        when(saleRepo.findById(id)).thenReturn(Optional.of(sale)).thenReturn(Optional.empty());
        when(receiptRepo.findById(id)).thenReturn(Optional.of(receipt)).thenReturn(Optional.empty());
        doNothing().when(saleRepo).deleteById(id);
        doNothing().when(receiptRepo).deleteBySale_Id(id);

        Optional<Sale> existingSale = saleRepo.findById(id);
        Optional<Receipt> existingReceipt = receiptRepo.findById(id);
        saleRepo.deleteById(id);
        receiptRepo.deleteBySale_Id(id);
        Optional<Sale> deletedSale = saleRepo.findById(id);
        Optional<Receipt> deletedReceipt = receiptRepo.findById(id);


        // Verify
        verify(saleRepo, times(2)).findById(id);
        verify(receiptRepo, times(2)).findById(id);
        verify(saleRepo, times(1)).deleteById(id);
        verify(receiptRepo, times(1)).deleteBySale_Id(id);
        assertNull(deletedSale.orElse(null));
        assertNull(deletedReceipt.orElse(null));
        assertNotNull(existingSale);
        assertNotNull(existingReceipt);
    }

    @Test
    void findBetweenDate() {

        // Given
        ProductSale productSale = new ProductSale();
        List<Sale> sales = List.of(new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(),
                                            List.of(productSale),
                                            null),
                                   new Sale(1, BigDecimal.valueOf(100), BigDecimal.valueOf(100),
                                            LocalDateTime.now(),
                                            List.of(productSale),
                                            null));
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // When
        when(saleRepo.findBySaleDateBetween(startDate, endDate)).thenReturn(sales);
        List<Sale> ret = saleRepo.findBySaleDateBetween(startDate, endDate);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(startDate, endDate);
        assertEquals(ret, sales);
        assertEquals(2, ret.size());
    }

    @Test
    void getTotalProductSaleBetween() {
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
        int totalQuantity = saleServ.getTotalProductSaleBetween(startDate, endDate);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(any(LocalDateTime.class),
                                                         any(LocalDateTime.class));
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
        BigDecimal totalAmount = saleServ.getTotalSalesAmountBetween(startDate, endDate);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(any(LocalDateTime.class),
                                                         any(LocalDateTime.class));
        assertEquals(BigDecimal.valueOf(100), totalAmount);
    }
}
