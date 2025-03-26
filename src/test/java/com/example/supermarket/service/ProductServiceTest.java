package com.example.supermarket.service;


import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Stock;
import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.ProductRepository;
import com.sun.jdi.request.DuplicateRequestException;
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
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      List.of(stock));


        // When
        when(supplierService.createNewSupplier(any(Supplier.class))).thenReturn(supplier);
        when(productRepository.existsByNameAndStocks_Supplier_Id(anyString(), anyInt())).thenReturn(false);
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);


        supplierService.createNewSupplier(supplier);
        productRepository.existsByNameAndStocks_Supplier_Id(product.getName(),
                                                            stock.getSupplier().getId());
        categoryRepository.findByName(category.getName());
        productRepository.save(product);


        // Verify
        verify(supplierService, times(1)).createNewSupplier(supplier);
        verify(productRepository, times(1)).existsByNameAndStocks_Supplier_Id(product.getName(),
                                                                              stock.getSupplier().getId());
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
    void testSaveWhenProductAlreadyExist() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), null,
                                supplier);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      List.of(stock));

        // When
        when(productRepository.existsByNameAndStocks_Supplier_Id(anyString(), anyInt())).thenThrow(new DuplicateRequestException());


        // Verify
        verify(productRepository, times(0)).existsByNameAndStocks_Supplier_Id(product.getName(),
                                                                              stock.getSupplier().getId());
        assertThrows(DuplicateRequestException.class,
                     () -> productRepository.existsByNameAndStocks_Supplier_Id(product.getName(),
                                                                               stock.getSupplier().getId()));


    }

    @Test
    void testUpdate() {

        // Given
        Supplier supplier = new Supplier(1, "Supplier Name", "Address", "123456789", "email" +
                "@example.com");
        Category category = new Category(1, "Food");
        Stock stock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), null,
                                supplier);
        Product product = new Product(1, "Apple", BigDecimal.valueOf(1.5), category,
                                      List.of(stock));
        Supplier modSupplier = new Supplier(1, "Supplier", "Address", "123456789", "email" +
                "@example.com");
        Stock modStock = new Stock(1, 10, LocalDate.now(), LocalDate.now().plusDays(10), null,
                                   supplier);
        Product modProduct = new Product(1, "modApple", BigDecimal.valueOf(1.5), category,
                                         List.of(stock));

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


    /*  @Test
//      void testDeleteAll() {
//          // GIVEN
//          List<Product> products = List.of(
//                  new Product(1, "Nome", new BigDecimal(12), null, null),
//                  new Product(2, "Nome", new BigDecimal(15), null, null));
//          Page<Product> pagedProduct = new PageImpl<>(products, PageRequest.of(page, 20),
products.size());
//
//          // WHEN
//          when(productService.findAll(page)).thenReturn(products);
//          productService.deleteAll();
//          verify(productService, times(1)).deleteAll();
//          when(productService.findAll()).thenReturn(null);
//
//          // VERIFY
//          List<Product> deletedProducts = productService.findAll();
//          assertNull(deletedProducts);
//
//      }
//  */
    @Test
    void testFindAll() {

        // Given
        int page = 0;
        Category category = new Category(1, "Categoria");
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(15), category, null),
                new Product(2, "Nome", new BigDecimal(15), category, null));

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

        // Given
        Category category = new Category(1, "Categoria");
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(15), category, null),
                new Product(2, "Nome", new BigDecimal(15), category, null));

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
        Product product = new Product(id, "Nome", new BigDecimal(id), null, null);

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
        Category category = new Category(1, "Categoria");
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(15), category, null),
                new Product(2, "Nome", new BigDecimal(15), category, null));

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
                new Product(1, "Nome", new BigDecimal(22), null, null),
                new Product(2, "Nome", new BigDecimal(26), null, null));

        // When
        when(productRepository.findByCategoryName(categoryName)).thenReturn(products);
        List<Product> ret = productRepository.findByCategoryName(categoryName);

        // Verify
        assertEquals(products, ret);
        assertNotNull(ret);
        assertEquals(2, ret.size());
        verify(productRepository, times(1)).findByCategoryName(categoryName);
    }

    @Test
    void testFindByCategoryNameException() {

        // Given
        String categoryName = "Categoria-A";
        List<Product> products = List.of(
                new Product(1, "Nome", new BigDecimal(22), null, null),
                new Product(2, "Nome", new BigDecimal(26), null, null));

        // When
        when(productRepository.findByCategoryName(categoryName)).thenThrow(new EntityNotFoundException());

        // Verify
        verify(productRepository, times(0)).findByCategoryName(categoryName);
        assertThrows(EntityNotFoundException.class,
                     () -> productRepository.findByCategoryName(categoryName));

    }

    //    @Test
    //    void testFindByExpirationDate() {
    //
    //        // GIVEN
    //        LocalDate expirationDate = LocalDate.now();
    //        List<Product> products = List.of(
    //                new Product(1, "Nome", new BigDecimal(22), null, null),
    //                new Product(2, "Nome", new BigDecimal(22), null, null));
    //
    //        // WHEN
    //        when(productService.findByExpirationDate(expirationDate)).thenReturn(products);
    //        List<Product> ret = productService.findByExpirationDate(expirationDate);
    //
    //        // VERIFY
    //        assertEquals(products, ret);
    //        assertNotNull(ret);
    //        assertEquals(2, ret.size());
    //        verify(productService, times(1)).findByExpirationDate(expirationDate);
    //    }
    //
    //    @Test
    //    void testFindByName() {
    //
    //        // GIVEN
    //        String name = "nome";
    //        List<Product> products = List.of(
    //                new Product(1, name, new BigDecimal(22), null, null),
    //                new Product(2, name, new BigDecimal(15), null, null));
    //
    //        // WHEN
    //        when(productService.findByName(name)).thenReturn(products);
    //        List<Product> ret = productService.findByName(name);
    //
    //        // VERIFY
    //        assertEquals(products, ret);
    //        assertNotNull(ret);
    //        assertEquals(2, ret.size());
    //        verify(productService, times(1)).findByName(name);
    //
    //    }
    //
    //    @Test
    //    void testFindBySellingPrice() {
    //
    //        // GIVEN
    //        double sellingPrice = 15.24;
    //        List<Product> products = List.of(
    //                new Product(1, "Nome", new BigDecimal(sellingPrice), null, null),
    //                new Product(2, "Nome", new BigDecimal(sellingPrice), null, null));
    //
    //        // WHEN
    //        when(productService.findBySellingPrice(sellingPrice)).thenReturn(products);
    //        List<Product> ret = productService.findBySellingPrice(sellingPrice);
    //
    //        // VERIFY
    //        assertEquals(products, ret);
    //        assertEquals(2, ret.size());
    //        assertNotNull(ret);
    //        verify(productService, times(1)).findBySellingPrice(sellingPrice);
    //    }
    //
    //    @Test
    //    void testFindByStockQuantity() {
    //
    //        // GIVEN
    //        int quantity = 15;
    //        List<Product> products = List.of(
    //                new Product(1, "Nome", new BigDecimal(22), null, null),
    //                new Product(2, "Nome", new BigDecimal(22), null, null));
    //
    //        // WHEN
    //        when(productService.findByStockQuantity(quantity)).thenReturn(products);
    //        List<Product> ret = productService.findByStockQuantity(quantity);
    //
    //        // VERIFY
    //        assertEquals(products, ret);
    //        assertNotNull(ret);
    //        assertEquals(2, ret.size());
    //        verify(productService, times(1)).findByStockQuantity(quantity);
    //
    //    }
    //
    //    @Test
    //    void testFindBySupplierName() {
    //
    //        // GIVEN
    //        String supplierName = "Nome Fornitore";
    //        List<Product> products = List.of(
    //                new Product(1, "Nome", new BigDecimal(22), null, null),
    //                new Product(2, "Nome", new BigDecimal(22), null, null));
    //
    //        // WHEN
    //        when(productService.findBySupplierName(supplierName)).thenReturn(products);
    //        List<Product> ret = productService.findBySupplierName(supplierName);
    //
    //        // VERIFY
    //        assertEquals(products, ret);
    //        assertNotNull(ret);
    //        assertEquals(2, ret.size());
    //        verify(productService, times(1)).findBySupplierName(supplierName);
    //    }
    //
    //
    //
    //    @Test
    //    void testDeleteById() {
    //        // GIVEN
    //        int id = 1;
    //        Product product = new Product(id, "Nome", new BigDecimal(id), null, null);
    //
    //        // WHEN
    //        when(productRepository.findById(id))
    //                .thenReturn(Optional.of(product))
    //                .thenReturn(Optional.empty());
    //        doNothing().when(productRepository).deleteById(id);
    //
    //        // Chiamata per ottenere il prodotto (prima della cancellazione)
    //        Optional<Product> existingProduct = productRepository.findById(id);
    //        // Cancelliamo il prodotto
    //        productRepository.deleteById(id);
    //        // Chiamata per verificare che il prodotto non esista più
    //        Optional<Product> deletedProduct = productRepository.findById(id);
    //
    //        // VERIFY
    //        verify(productRepository, times(2)).findById(id); // verifica che findById sia stato
    //        chiamato due volte
    //        verify(productRepository, times(1)).deleteById(id);
    //        assertNull(deletedProduct.orElse(null));
    //        assertNotNull(existingProduct);
    //    }
    //
    //
}
//
//
//
