package com.example.supermarket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.supermarket.entity.Category;
import com.example.supermarket.repo.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Test
    void testFindById() {

        // GIVEN
        Category category = new Category();
        int id = 17;
        category.setId(id);

        // WHEN
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Optional<Category> ret = categoryRepository.findById(id);

        assertEquals(ret.get().getId(), category.getId());
        verify(categoryRepository, times(1)).findById(id);
    }
}
