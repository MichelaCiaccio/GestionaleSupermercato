package com.example.supermarket.repo;

import com.example.supermarket.entity.Sale;
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
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Override
    @Nonnull
    Optional<Sale> findById(@Nullable Integer id);

    @Nonnull
    Page<Sale> findAll(@Nullable Pageable pageable);

    List<Sale> findByProductSales_Product_Name(String productSale);

    List<Sale> findBySaleDate(LocalDate saleDate);

}
