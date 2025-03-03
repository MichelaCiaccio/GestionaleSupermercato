package com.example.supermarket.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(categoryRepository.findById(id)).thenReturn(category);
        Category ret = categoryRepository.findById(id);

        assertEquals(ret.getId(), category.getId());
        verify(categoryRepository, times(1)).findById(id);
    }
}
