package com.example.supermarket.service;


import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierService supplierService;

    @Test
    void testSaveSuccessfully() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), null,
                                supplier);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), null, false, category,
                                      List.of(stock), null);


        // When
        when(supplierService.createNewSupplier(any(Supplier.class))).thenReturn(supplier);
        when(productRepository.findByNameAndStocks_Supplier_IdAndStocks_ExpirationDate(anyString(), anyInt(), any(LocalDate.class))).thenReturn(Optional.empty());
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);


        supplierService.createNewSupplier(supplier);
        productRepository.findByNameAndStocks_Supplier_IdAndStocks_ExpirationDate(product.getName(),
                                                                                  stock.getSupplier().getId(), stock.getExpirationDate());
        categoryRepository.findByName(category.getName());
        productRepository.save(product);


        // Verify
        verify(supplierService, times(1)).createNewSupplier(supplier);
        verify(productRepository, times(1)).findByNameAndStocks_Supplier_IdAndStocks_ExpirationDate(product.getName(),
                                                                                                    stock.getSupplier().getId(), stock.getExpirationDate());
        verify(categoryRepository, times(1)).findByName(product.getCategory().getName());
        verify(productRepository, times(1)).save(product);

        // Assert
        assertNotNull(supplier);
        assertNotNull(category);
        assertNotNull(product);
        assertEquals("Apple", product.getName());
        assertEquals(BigDecimal.valueOf(1.5), product.getSellingPrice());
        assertEquals(category, product.getCategory());
        assertEquals(1, product.getStocks().size());


    }

    @Test
    void testUpdate() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), null,
                                supplier);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), null, false, category,
                                      List.of(stock), null);
        Product modProduct = new Product(1, "modApple", BigDecimal.valueOf(1.5), null, false,
                                         category,
                                         List.of(stock), null);

        // When
        when(productRepository.findById(1)).thenReturn(Optional.of(product)).thenReturn(Optional.of(modProduct));
        Optional<Product> existingProduct = productRepository.findById(1);
        productService.updateProduct(1, modProduct);
        Optional<Product> updatedProduct = productRepository.findById(1);

        // Verify
        verify(productRepository, times(2)).findById(1);
        assertNotEquals(existingProduct, updatedProduct);
        assertNotNull(existingProduct);
        assertNotNull(updatedProduct);
    }

    @Test
    void testDeleteById() {

        // Given
        int id = 1;
        Product product = new Product(id, "Nome", new BigDecimal(id), null, false, null, null,
                                      null);

        // When
        when(productRepository.findById(id))
                .thenReturn(Optional.of(product))
                .thenReturn(Optional.empty());
        doNothing().when(productRepository).deleteById(id);


        Optional<Product> existingProduct = productRepository.findById(id);
        productRepository.deleteById(id);
        Optional<Product> deletedProduct = productRepository.findById(id);

        // VERIFY
        verify(productRepository, times(2)).findById(id);
        verify(productRepository, times(1)).deleteById(id);
        assertNull(deletedProduct.orElse(null));
        assertNotNull(existingProduct);
    }

    @Test
    void testDeleteByIdException() {

        // Given
        int id = 1;

        // When
        when(productRepository.findById(id)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepository, times(0)).findById(id);
        assertThrows(EntityNotFoundException.class, () -> productRepository.findById(id));

    }

    @Test
    void testDeleteAll() {

        // Given
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(12), null, false, null, null, null),
                new Product(2, "Nome", new BigDecimal(15), null, false, null, null, null));


        // When
        when(productRepository.findAll()).thenReturn(products).thenReturn(null);
        doNothing().when(productRepository).deleteAll();
        List<Product> existingProduct = productRepository.findAll();
        productRepository.deleteAll();
        List<Product> deletedProduct = productRepository.findAll();


        //Verify
        verify(productRepository, times(1)).deleteAll();
        assertNotNull(existingProduct);
        assertNull(deletedProduct);

    }

    @Test
    void testDeleteAllException() {

        // When
        when(productRepository.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepository, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, () -> productRepository.findAll());

    }


    @Test
    void testFindAll() {

        // Given
        Category category = new Category(1, "Categoria");
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(15), null, false, category, null, null),
                new Product(2, "Nome", new BigDecimal(15), null, false, category, null, null));

        // When
        when(productRepository.findAll()).thenReturn(products);
        List<Product> ret = productRepository.findAll();

        // Verify
        verify(productRepository, times(1)).findAll();
        assertNotNull(ret);
        assertEquals(2, ret.size());

    }

    @Test
    void testFindAllException() {


        // When
        when(productRepository.findAll()).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepository, times(0)).findAll();
        assertThrows(EntityNotFoundException.class, () -> productRepository.findAll());

    }

    @Test
    void testFindById() {

        // GIVEN
        int id = 1;
        Product product = new Product(id, "Nome", new BigDecimal(id), null, false, null, null,
                                      null);

        // WHEN
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Optional<Product> ret = productRepository.findById(id);

        // VERIFY
        assertEquals(ret.get().getId(), product.getId());
        assertNotNull(ret);
        verify(productRepository, times(1)).findById(id);

    }

    @Test
    void testFindByIdException() {

        // Given
        int id = 1;

        // When
        when(productRepository.findById(id)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepository, times(0)).findById(id);
        assertThrows(EntityNotFoundException.class, () -> productRepository.findById(id));

    }

    @Test
    void testFindByCategoryName() {

        // Given
        String categoryName = "Categoria-A";
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, false, null, null, null),
                new Product(2, "Nome", new BigDecimal(26), null, false, null, null, null));

        // When
        when(productRepository.findByCategoryNameAndRemovedFalse(categoryName)).thenReturn(products);
        List<Product> ret = productRepository.findByCategoryNameAndRemovedFalse(categoryName);

        // Verify
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productRepository, times(1)).findByCategoryNameAndRemovedFalse(categoryName);
    }

    @Test
    void testFindByCategoryNameException() {

        // Given
        String categoryName = "Categoria-A";

        // When
        when(productRepository.findByCategoryNameAndRemovedFalse(categoryName)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepository, times(0)).findByCategoryNameAndRemovedFalse(categoryName);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findByCategoryNameAndRemovedFalse(categoryName));

    }

    @Test
    void testFindByExpirationDate() {

        // Given
        LocalDate expirationDate = LocalDate.now().plusDays(50);
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, false, null, null, null),
                new Product(2, "Nome", new BigDecimal(22), null, false, null, null, null));

        // When
        when(productRepository.findByStocks_ExpirationDateAndRemovedFalse(expirationDate)).thenReturn(products);
        List<Product> ret =
                productRepository.findByStocks_ExpirationDateAndRemovedFalse(expirationDate);

        // Verify
        verify(productRepository, times(1)).findByStocks_ExpirationDateAndRemovedFalse(expirationDate);
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());

    }

    @Test
    void testFindByExpirationDateException() {

        // Given
        LocalDate expirationDate = LocalDate.now().plusDays(50);

        // When
        when(productRepository.findByStocks_ExpirationDateAndRemovedFalse(expirationDate)).thenThrow(new EntityNotFoundException());


        // Verify
        verify(productRepository, times(0)).findByStocks_ExpirationDateAndRemovedFalse(expirationDate);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findByStocks_ExpirationDateAndRemovedFalse(expirationDate));

    }

    @Test
    void testFindByName() {

        // Given
        String name = "nome";
        List<Product> products = List.of(
                new Product(1, name, new BigDecimal(22), null, false, null, null, null),
                new Product(2, name, new BigDecimal(15), null, false, null, null, null));

        // When
        when(productRepository.findByNameAndRemovedFalse(name)).thenReturn(products);
        List<Product> ret = productRepository.findByNameAndRemovedFalse(name);

        // Verify
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productRepository, times(1)).findByNameAndRemovedFalse(name);

    }

    @Test
    void testFindByNameException() {

        // Given
        String name = "nome";


        // When
        when(productRepository.findByNameAndRemovedFalse(name)).thenThrow(new EntityNotFoundException());


        // Verify
        verify(productRepository, times(0)).findByNameAndRemovedFalse(name);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findByNameAndRemovedFalse(name));


    }

    @Test
    void testFindBySellingPrice() {

        // Given
        double sellingPrice = 15.24;
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(sellingPrice), null, false, null, null,
                            null),
                new Product(2, "Nome", new BigDecimal(sellingPrice), null, false, null, null,
                            null));

        // WHEN
        when(productRepository.findBySellingPriceAndRemovedFalse(sellingPrice)).thenReturn(products);
        List<Product> ret = productRepository.findBySellingPriceAndRemovedFalse(sellingPrice);

        // Verify
        assertEquals(products, ret);
        assertEquals(2, ret.size());
        assertNotNull(ret);
        verify(productRepository, times(1)).findBySellingPriceAndRemovedFalse(sellingPrice);
    }

    @Test
    void testFindBySellingPriceException() {

        // Given
        double sellingPrice = 15.24;

        // When
        when(productRepository.findBySellingPriceAndRemovedFalse(sellingPrice)).thenThrow(new EntityNotFoundException());


        // Verify
        verify(productRepository, times(0)).findBySellingPriceAndRemovedFalse(sellingPrice);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findBySellingPriceAndRemovedFalse(sellingPrice));


    }

    @Test
    void testFindByStockQuantity() {

        // Given
        int quantity = 15;
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, false, null, null, null),
                new Product(2, "Nome", new BigDecimal(22), null, false, null, null, null));

        // When
        when(productRepository.findByStocks_QuantityAndRemovedFalse(quantity)).thenReturn(products);
        List<Product> ret = productRepository.findByStocks_QuantityAndRemovedFalse(quantity);

        // Verify
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productRepository, times(1)).findByStocks_QuantityAndRemovedFalse(quantity);

    }

    @Test
    void testFindByQuantityException() {

        // Given
        int quantity = 15;


        // When
        when(productRepository.findByStocks_QuantityAndRemovedFalse(quantity)).thenThrow(new EntityNotFoundException());


        // Verify
        verify(productRepository, times(0)).findByStocks_QuantityAndRemovedFalse(quantity);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findByStocks_QuantityAndRemovedFalse(quantity));


    }


    @Test
    void testFindBySupplierName() {

        // Given
        String supplierName = "Nome Fornitore";
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, false, null, null, null),
                new Product(2, "Nome", new BigDecimal(22), null, false, null, null, null));

        // When
        when(productRepository.findByStocks_Supplier_NameAndRemovedFalse(supplierName)).thenReturn(products);
        List<Product> ret =
                productRepository.findByStocks_Supplier_NameAndRemovedFalse(supplierName);

        // Verify
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productRepository, times(1)).findByStocks_Supplier_NameAndRemovedFalse(supplierName);
    }

    @Test
    void testFindBySupplierNameException() {

        // Given
        String supplierName = "Nome Fornitore";

        // When
        when(productRepository.findByStocks_Supplier_NameAndRemovedFalse(supplierName)).thenThrow(new EntityNotFoundException());


        // Verify
        verify(productRepository, times(0)).findByStocks_Supplier_NameAndRemovedFalse(supplierName);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findByStocks_Supplier_NameAndRemovedFalse(supplierName));


    }


}

