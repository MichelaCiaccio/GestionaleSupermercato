package com.example.supermarket.service;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.entity.Product;
import com.example.supermarket.repo.DiscountRepository;
import com.example.supermarket.repo.ProductRepository;
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
class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepo;

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private DiscountService discountServ;

    @Test
    void findAllDiscountSorted() {
        // Given
        List<Discount> discounts = List.of(new Discount(1, "Discount", 30, LocalDate.of(2026, 06,
                                                                                        30)
                , true), new Discount(1, "Discount", 30, LocalDate.of(2026, 06,
                                                                      30)
                , true));

        // When
        when(discountRepo.findAll()).thenReturn(discounts);
        List<Discount> ret = discountRepo.findAll();

        // Verify
        verify(discountRepo, times(1)).findAll();
        assertNotNull(ret);
        assertEquals(2, ret.size());

    }

    @Test
    void findAllDiscountSortedException() {

        // When
        when(discountRepo.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(discountRepo, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, () -> discountRepo.findAll());
    }

    @Test
    void createNewDiscount() {

        // Given
        Discount discount = new Discount(1, "Discount", 30, LocalDate.of(2026, 06,
                                                                         30)
                , true);
        Product product1 = new Product(1, "Nome", new BigDecimal(12), false, null, null,
                                       discount, null);
        Product product2 = new Product(2, "Nome", new BigDecimal(15), false, null, null, discount
                , null);

        // When
        when(productRepo.findByIdAndRemovedFalse(1)).thenReturn(Optional.of(product1));
        when(productRepo.findByIdAndRemovedFalse(2)).thenReturn(Optional.of(product2));
        when(discountRepo.save(any(Discount.class))).thenReturn(discount);
        discountServ.createNewDiscount(discount, List.of(1, 2));

        // Verify
        verify(productRepo, times(2)).save(any(Product.class));
        assertEquals(discount, product1.getDiscount());
        assertEquals(discount, product2.getDiscount());
    }

    @Test
    void createNewDiscountException() {

        // Given
        Product product1 = new Product(1, "Nome", new BigDecimal(12), false, null, null,
                                       null, null);

        // When
        when(productRepo.findByIdAndRemovedFalse(1)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepo, times(0)).findByIdAndRemovedFalse(1);
        assertThrows(EntityNotFoundException.class, () -> productRepo.findByIdAndRemovedFalse(1));
    }

    @Test
    void updateDiscount() {
    }

    @Test
    void updateDiscountStatus() {
    }

    @Test
    void deleteAll() {

        // Given
        Discount discount = new Discount(1, "Discount", 30, LocalDate.of(2026, 06,
                                                                         30)
                , true);
        Product product = new Product(1, "Nome", new BigDecimal(12), false, null, null,
                                      discount, null);

        // When
        when(discountRepo.findAll()).thenReturn(List.of(discount));
        when(productRepo.findByDiscountIn(List.of(discount))).thenReturn(List.of(product));
        doNothing().when(discountRepo).deleteAll();
        discountServ.deleteAll();

        // Verify
        verify(discountRepo, times(1)).findAll();
        verify(productRepo, times(1)).findByDiscountIn(List.of(discount));
        assertNull(product.getDiscount());

    }

    @Test
    void deleteAllException() {

        // When
        when(discountRepo.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(discountRepo, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, () -> discountRepo.findAll());
    }


    @Test
    void deleteById() {

        // Given
        Discount discount = new Discount(1, "Discount", 30, LocalDate.of(2026, 06,
                                                                         30)
                , true);
        Product product1 = new Product(1, "Nome", new BigDecimal(12), false, null, null,
                                       discount, null);
        Product product2 = new Product(2, "Nome", new BigDecimal(15), false, null, null, discount
                , null);

        // When
        when(discountRepo.findById(1)).thenReturn(Optional.of(discount));
        when(productRepo.findByDiscountId(1)).thenReturn(List.of(product1, product2));
        doNothing().when(discountRepo).deleteById(1);
        discountServ.deleteById(1);

        // Verify
        verify(discountRepo, times(1)).findById(1);
        verify(productRepo, times(1)).findByDiscountId(1);
        assertNull(product1.getDiscount());
        assertNull(product2.getDiscount());
    }


    @Test
    void deleteByIdException() {

        // Given
        Discount discount = new Discount(1, "Discount", 30, LocalDate.of(2026, 06,
                                                                         30)
                , true);

        // When
        when(discountRepo.findById(1)).thenThrow(new EntityNotFoundException());

        // Verify
        // Verify
        verify(discountRepo, times(0)).findById(1);
        assertThrows(EntityNotFoundException.class, () -> discountRepo.findById(1));
    }
}