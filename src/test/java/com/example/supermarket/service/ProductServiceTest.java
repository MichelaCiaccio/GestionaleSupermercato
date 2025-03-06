package com.example.supermarket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.supermarket.entity.Product;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductService productService;

    @Test
    void testDeleteAll() {
        // GIVEN
        List<Product> products = List.of(
                new Product(1, "Nome", 12, null, new HashSet<>()),
                new Product(2, "Nome", 15, null, new HashSet<>()));

        // WHEN
        when(productService.findAll()).thenReturn(products);
        productService.deleteAll();
        verify(productService, times(1)).deleteAll();
        when(productService.findAll()).thenReturn(null);

        // VERIFY
        List<Product> deletedProducts = productService.findAll();
        assertNull(deletedProducts);

    }

    @Test
    void testDeleteById() {

        // GIVEN
        int id = 1;
        Product product = new Product(id, "Nome", 12, null, new HashSet<>());

        // WHEN
        when(productService.findById(id)).thenReturn(Optional.of(product));
        productService.deleteById(id);
        verify(productService, times(1)).deleteById(id);
        when(productService.findById(id)).thenReturn(null);

        // VERIFY
        Optional<Product> deletedProduct = productService.findById(id);
        assertNull(deletedProduct);

    }

    @Test
    void testFindAll() {
        // GIVEN
        List<Product> products = List.of(
                new Product(1, "Nome", 12, null, new HashSet<>()),
                new Product(2, "Nome", 15, null, new HashSet<>()));

        // WHEN
        when(productService.findAll()).thenReturn(products);
        List<Product> ret = productService.findAll();

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findAll();
    }

    @Test
    void testFindByCategory() {

    }

    @Test
    void testFindByExpirationDate() {

    }

    @Test
    void testFindById() {

        // GIVEN
        int id = 1;
        Product product = new Product(id, "Nome", 12, null, new HashSet<>());

        // WHEN
        when(productService.findById(id)).thenReturn(Optional.of(product));
        Optional<Product> ret = productService.findById(id);

        // VERIFY
        assertEquals(product.getId(), ret.get().getId());
        assertNotNull(ret);
        verify(productService, times(1)).findById(id);

    }

    @Test
    void testFindByName() {

        // GIVEN
        String name = "nome";
        List<Product> products = List.of(
                new Product(1, name, 12, null, new HashSet<>()),
                new Product(2, name, 15, null, new HashSet<>()));

        // WHEN
        when(productService.findByName(name)).thenReturn(products);
        List<Product> ret = productService.findByName(name);

        // VERIFY
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findByName(name);

    }

    @Test
    void testFindBySellingPrice() {

    }

    @Test
    void testFindByStockQuantity() {

    }

    @Test
    void testFindBySupplierName() {

    }

    @Test
    void testSave() {

    }
}
