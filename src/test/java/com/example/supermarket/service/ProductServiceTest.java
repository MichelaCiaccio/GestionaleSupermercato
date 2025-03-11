package com.example.supermarket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.example.supermarket.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.supermarket.entity.Product;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void testDeleteAll() {
        // GIVEN
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(12), null, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(15), null, new HashSet<>()));

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
    void testFindAll() {
        // GIVEN
        Category category = new Category(1, "Categoria");
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(15), category, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(15), category, new HashSet<>()));

        // WHEN
        when(productService.findAll()).thenReturn(products);
        List<Product> ret = productService.findAll();

        // VERIFY
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findAll();
    }

    @Test
    void testFindById() {

        // GIVEN
        int id = 1;
        Product product = new Product(id, "Nome", new BigDecimal(id), null, new HashSet<>());

        // WHEN
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Optional<Product> ret = productRepository.findById(id);

        // VERIFY
        assertEquals(product.getId(), ret.get().getId());
        assertNotNull(ret);
        verify(productRepository, times(1)).findById(id);

    }

    @Test
    void testFindByIdException() {
        // GIVEN
        int id = 1;

        // WHEN
        when(productService.findById(id)).thenThrow(new EntityNotFoundException("Product with is" + id + " not found"));

        // VERIFY
        assertThrows(EntityNotFoundException.class, () -> productService.findById(id));
        verify(productService, times(1)).findById(id);

    }

    @Test
    void testfindByCategoryName() {

        // GIVEN
        String categoryName = "Categoria-A";
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(26), null, new HashSet<>()));

        // WHEN
        when(productService.findByCategoryName(categoryName)).thenReturn(products);
        List<Product> ret = productService.findByCategoryName(categoryName);

        // VERIFY
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findByCategoryName(categoryName);
    }

    @Test
    void testFindByExpirationDate() {

        // GIVEN
        LocalDate expirationDate = LocalDate.now();
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(22), null, new HashSet<>()));

        // WHEN
        when(productService.findByExpirationDate(expirationDate)).thenReturn(products);
        List<Product> ret = productService.findByExpirationDate(expirationDate);

        // VERIFY
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findByExpirationDate(expirationDate);
    }

    @Test
    void testFindByName() {

        // GIVEN
        String name = "nome";
        List<Product> products = List.of(
                new Product(1, name, new BigDecimal(22), null, new HashSet<>()),
                new Product(2, name, new BigDecimal(15), null, new HashSet<>()));

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

        // GIVEN
        double sellingPrice = 15.24;
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(sellingPrice), null, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(sellingPrice), null, new HashSet<>()));

        // WHEN
        when(productService.findBySellingPrice(sellingPrice)).thenReturn(products);
        List<Product> ret = productService.findBySellingPrice(sellingPrice);

        // VERIFY
        assertEquals(products, ret);
        assertEquals(2, ret.size());
        assertNotNull(ret);
        verify(productService, times(1)).findBySellingPrice(sellingPrice);
    }

    @Test
    void testFindByStockQuantity() {

        // GIVEN
        int quantity = 15;
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(22), null, new HashSet<>()));

        // WHEN
        when(productService.findByStockQuantity(quantity)).thenReturn(products);
        List<Product> ret = productService.findByStockQuantity(quantity);

        // VERIFY
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findByStockQuantity(quantity);

    }

    @Test
    void testFindBySupplierName() {

        // GIVEN
        String supplierName = "Nome Fornitore";
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, new HashSet<>()),
                new Product(2, "Nome", new BigDecimal(22), null, new HashSet<>()));

        // WHEN
        when(productService.findBySupplierName(supplierName)).thenReturn(products);
        List<Product> ret = productService.findBySupplierName(supplierName);

        // VERIFY
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productService, times(1)).findBySupplierName(supplierName);
    }

    @Test
    void testSave() {

        // GIVEN
        int productId = 1;
        Category category = new Category(1, "categoria");
        Product product = new Product(productId, "Nome", new BigDecimal(23), category, new HashSet<>());

        // WHEN
        when(productRepository.findById(productId)).thenReturn(null).thenReturn(Optional.of(product));
        when(categoryRepository.findByName("Categoria")).thenReturn(null)
                .thenReturn(Optional.of(category));
        Optional<Product> noProduct = productRepository.findById(productId);
        Optional<Category> noCategory = categoryRepository.findByName("Categoria");
        productRepository.save(product);
        Optional<Product> newProduct = productRepository.findById(productId);
        Optional<Category> newCategory = categoryRepository.findByName("Categoria");

        // VERIFY
        verify(productRepository, times(2)).findById(productId);
        verify(categoryRepository, times(2)).findByName("Categoria");
        verify(productRepository, times(1)).save(product);
        assertNotNull(newCategory);
        assertNotNull(newProduct);
        assertNull(noProduct);
        assertNull(noCategory);
    }

    @Test
    void testDeleteById() {
        // GIVEN
        int id = 1;
        Product product = new Product(id, "Nome", new BigDecimal(id), null, new HashSet<>());

        // WHEN
        when(productRepository.findById(id))
                .thenReturn(Optional.of(product))
                .thenReturn(Optional.empty());
        doNothing().when(productRepository).deleteById(id);

        // Chiamata per ottenere il prodotto (prima della cancellazione)
        Optional<Product> existingProduct = productRepository.findById(id);
        // Cancelliamo il prodotto
        productRepository.deleteById(id);
        // Chiamata per verificare che il prodotto non esista più
        Optional<Product> deletedProduct = productRepository.findById(id);

        // VERIFY
        verify(productRepository, times(2)).findById(id); // verifica che findById sia stato chiamato due volte
        verify(productRepository, times(1)).deleteById(id);
        assertNull(deletedProduct.orElse(null));
        assertNotNull(existingProduct);
    }
}
