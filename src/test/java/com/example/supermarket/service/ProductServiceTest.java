//package com.example.supermarket.service;


//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {

// @Mock
//private ProductRepository productRepository;







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
/// *    @Test
//    void testFindAll() {
//        // GIVEN
//        int page = 0;
//        Category category = new Category(1, "Categoria");
//        List<Product> products = List.of(
//                new Product(1, "Nome", new BigDecimal(15), category, null),
//                new Product(2, "Nome", new BigDecimal(15), category, null));
//
//        // WHEN
//        when(productService.findAll(page)).thenReturn(products);
//        Page<Product> ret = productService.findAll(page);
//
//        // VERIFY
//        assertNotNull(ret);
//        assertEquals(2, ret.size());
//        verify(productService, times(1)).findAll();
//    }*/
//
//    @Test
//    void testFindById() {
//
//        // GIVEN
//        int id = 1;
//        Product product = new Product(id, "Nome", new BigDecimal(id), null, null);
//
//        // WHEN
//        when(productRepository.findById(id)).thenReturn(Optional.of(product));
//        Optional<Product> ret = productRepository.findById(id);
//
//        // VERIFY
//        assertEquals(product.getId(), ret.get().getId());
//        assertNotNull(ret);
//        verify(productRepository, times(1)).findById(id);
//
//    }
//
//    @Test
//    void testFindByIdException() {
//        // GIVEN
//        int id = 1;
//
//        // WHEN
//        when(productService.findById(id)).thenThrow(new EntityNotFoundException("Product with
//        is" + id + " not found"));
//
//        // VERIFY
//        assertThrows(EntityNotFoundException.class, () -> productService.findById(id));
//        verify(productService, times(1)).findById(id);
//
//    }
//
//    @Test
//    void testfindByCategoryName() {
//
//        // GIVEN
//        String categoryName = "Categoria-A";
//        List<Product> products = List.of(
//                new Product(1, "Nome", new BigDecimal(22), null, null),
//                new Product(2, "Nome", new BigDecimal(26), null, null));
//
//        // WHEN
//        when(productService.findByCategoryName(categoryName)).thenReturn(products);
//        List<Product> ret = productService.findByCategoryName(categoryName);
//
//        // VERIFY
//        assertEquals(products, ret);
//        assertNotNull(ret);
//        assertEquals(2, ret.size());
//        verify(productService, times(1)).findByCategoryName(categoryName);
//    }
//
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
//}
//
//
//
