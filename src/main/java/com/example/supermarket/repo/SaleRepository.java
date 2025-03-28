package com.example.supermarket.repo;

import com.example.supermarket.entity.Sale;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Override
    @Nonnull
    Optional<Sale> findById(@Nullable Integer id);

    List<Sale> findByProductSales_Product_Name(String productSale);

    List<Sale> findBySaleDate(LocalDate saleDate);

}
