package com.example.supermarket.repo;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.entity.Product;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Nonnull
    Optional<Product> findByIdAndRemovedFalse(Integer id);

    @Nonnull
    Page<Product> findAll(@Nullable Pageable pageable);

    Page<Product> findByRemovedFalse(Pageable pageable);

    List<Product> findByNameAndRemovedFalse(String name);

    List<Product> findByCategoryNameAndRemovedFalse(String categoryName);

    List<Product> findBySellingPriceAndRemovedFalse(double sellingPrice);

    List<Product> findByStocks_Supplier_NameAndRemovedFalse(String supplierName);

    List<Product> findByStocks_QuantityAndRemovedFalse(int stockQuantity);

    List<Product> findByStocks_ExpirationDateAndRemovedFalse(LocalDate expirationDate);
    
    Optional<Product> findByNameAndStocks_Supplier_IdAndStocks_ExpirationDate(String productName,
                                                                              Integer supplierId,
                                                                              LocalDate expirationDate);

    List<Product> findByDiscountId(int discountId);

    List<Product> findByDiscountIn(List<Discount> discounts);


}
