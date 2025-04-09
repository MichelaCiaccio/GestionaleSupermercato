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
        int totalQuantity = saleServ.getTotalProductSoldBetween(startDate, endDate);

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
        BigDecimal totalAmount = saleServ.getTotalAmountSoldBetween(startDate, endDate);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(any(LocalDateTime.class),
                                                         any(LocalDateTime.class));
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
                saleServ.getBestSelling(startDate, endDate, product -> product);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(any(LocalDateTime.class),
                                                         any(LocalDateTime.class));
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
                saleServ.getBestSelling(startDate, endDate, Product::getCategory);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(any(LocalDateTime.class),
                                                         any(LocalDateTime.class));
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
                saleServ.getBestSelling(startDate, endDate, Product::getDiscount);

        // Verify
        verify(saleRepo, times(1)).findBySaleDateBetween(any(LocalDateTime.class),
                                                         any(LocalDateTime.class));
        assertEquals(3, bestSellingDeals.size()); // Verifica che siano 3 prodotti
        assertEquals(discount3, bestSellingDeals.get(0)); // Il prodotto più venduto
        assertEquals(discount2, bestSellingDeals.get(1)); // Il secondo più venduto
        assertEquals(discount1, bestSellingDeals.get(2)); // Il terzo più venduto

    }
}
