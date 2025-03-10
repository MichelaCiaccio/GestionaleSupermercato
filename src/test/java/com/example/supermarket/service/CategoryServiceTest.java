package com.example.supermarket.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.supermarket.entity.Category;

import jakarta.persistence.EntityNotFoundException;

//TO DO test save Exception
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryService categoryService;

    @Test
    void testDeleteAll() {

        // GIVEN
        List<Category> categories = List.of(
                new Category(1, "Fruits"),
                new Category(2, "Vegetables"));

        // WHEN
        when(categoryService.findAll()).thenReturn(categories);
        categoryService.deleteAll();
        verify(categoryService, times(1)).deleteAll();
        when(categoryService.findAll()).thenReturn(null);

        // VERIFY
        List<Category> delCategories = categoryService.findAll();
        assertNull(delCategories);
    }

    @Test
    void testDeleteAllException() {

        // WHEN
        doThrow(new EntityNotFoundException()).when(categoryService).deleteAll();

        // VERIFY
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteAll());
        verify(categoryService, times(1)).deleteAll();
    }

    @Test
    void testDeleteById() {

        // GIVEN
        int id = 1;
        Category category = new Category(1, "Fruits");

        // WHEN
        when(categoryService.findById(id)).thenReturn(category);
        categoryService.deleteById(id);
        when(categoryService.findById(id)).thenReturn(null);
        Category delCategory = categoryService.findById(id);

        // VERIFY

        verify(categoryService, times(1)).deleteById(id);
        assertNull(delCategory);

    }

    @Test
    void testDeleteByIdException() {
        // GIVEN
        int id = 1;

        // WHEN
        doThrow(new EntityNotFoundException()).when(categoryService).deleteById(id);

        // VERIFY
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(id));
        verify(categoryService, times(1)).deleteById(id);

    }

    @Test
    void testFindAll() {

        // GIVEN
        List<Category> categories = List.of(
                new Category(1, "Fruits"),
                new Category(2, "Vegetables"));

        // WHEN
        when(categoryService.findAll()).thenReturn(categories);
        List<Category> ret = categoryService.findAll();

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void testFindAllException() {

        // WHEN
        when(categoryService.findAll()).thenThrow(new EntityNotFoundException());

        // VERIFY
        assertThrows(EntityNotFoundException.class, () -> categoryService.findAll());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void testFindById() {

        // GIVEN
        int id = 1;
        Category category = new Category(1, "Fruits");

        // WHEN
        when(categoryService.findById(id)).thenReturn(category);
        Category ret = categoryService.findById(id);

        // VERIFY
        assertEquals(category.getId(), ret.getId());
        assertNotNull(ret);
        verify(categoryService, times(1)).findById(id);

    }

    @Test
    void testFindByIdException() {
        // GIVEN
        int id = 1;

        // WHEN
        when(categoryService.findById(id))
                .thenThrow(new EntityNotFoundException("Category with is" + id + " not found"));

        // VERIFY
        assertThrows(EntityNotFoundException.class, () -> categoryService.findById(id));
        verify(categoryService, times(1)).findById(id);

    }

    @Test
    void testFindByName() {

        // GIVEN
        String name = "Categoria";
        Category category = new Category(1, name);

        // WHEN
        when(categoryService.findByName(name)).thenReturn(category);
        Category ret = categoryService.findByName(name);

        // VERIFY
        assertNotNull(ret);
        assertEquals(category.getName(), ret.getName());
        verify(categoryService, times(1)).findByName(name);

    }

    @Test
    void testFindByNameException() {
        // GIVEN
        String name = "categoria";

        // WHEN
        when(categoryService.findByName(name))
                .thenThrow(new EntityNotFoundException("Category with name " + name + " not found"));

        // VERIFY
        assertThrows(EntityNotFoundException.class, () -> categoryService.findByName(name));
        verify(categoryService, times(1)).findByName(name);

    }

    @Test
    void testSave() {

        // GIVEN
        Category category = new Category(1, "Categoria");

        // WHEN
        categoryService.save(category);

        // VERIFY
        assertNotNull(category);
        verify(categoryService, times(1)).save(category);

    }

}
