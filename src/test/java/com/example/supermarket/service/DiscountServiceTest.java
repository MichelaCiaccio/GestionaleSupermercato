package com.example.supermarket.service;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.repo.DiscountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepo;

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
    }

    @Test
    void updateDiscount() {
    }

    @Test
    void updateDiscountStatus() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void deleteById() {
    }
}