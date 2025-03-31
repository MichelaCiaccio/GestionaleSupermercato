package com.example.supermarket.repo;

import com.example.supermarket.entity.Stock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findByProduct_Id(int id);

    Optional<Stock> findByProduct_NameAndSupplier_Name(String productName, String supplierName);
}
