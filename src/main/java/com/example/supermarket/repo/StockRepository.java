package com.example.supermarket.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.supermarket.entity.Stock;
import java.util.List;

import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByProductName(String name);

    Stock findByProductId(int productId);

    List<Stock> findBySupplierName(String namej);

    List<Stock> findByQuantityGreaterThan(int quantity);

    List<Stock> findByQuantityLessThan(int quantity);

    List<Stock> findByDeliveryDate(LocalDate deliveryDate);

    List<Stock> findByExpirationDate(LocalDate expirationDate);

    List<Stock> findByExpirationDateBetween(LocalDate startExpirationPeriod, LocalDate endExpirationPeriod);

    boolean existsByProductNameAndSupplierName(String productName, String supplierName);

}
