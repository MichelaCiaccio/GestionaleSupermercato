package com.example.supermarket.repo;

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

// Paginazione a 20
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Override
    @Nonnull
    Optional<Product> findById(@Nullable Integer id);


    @Nonnull
    Page<Product> findAll(@Nullable Pageable pageable);

    List<Product> findByName(String name);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findBySellingPrice(double sellingPrice);

    List<Product> findByStocks_Supplier_Name(String supplierName);

    List<Product> findByStocks_ExpirationDate(LocalDate expirationDate);

    List<Product> findByStocks_Quantity(int quantity);

    boolean existsByNameAndStocks_Supplier_Name(String name, String supplierName);

}
