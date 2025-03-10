package com.example.supermarket.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.supermarket.entity.Stock;
import java.util.List;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Supplier;

import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByProductName(String name);

    List<Stock> findBySupplierName(String namej);

    List<Stock> findByProductAndSupplier(Product product, Supplier supplier);

    List<Stock> findByQuantityGreaterThan(int quantity);

    List<Stock> findByQuantityLessThan(int quantity);

    List<Stock> findByDeliveryDate(LocalDate deliveryDate);

    List<Stock> findByExpirationDate(LocalDate expirationDate);

    List<Stock> findByExpirationDateAfter(LocalDate expirationDate);

    List<Stock> findByExpirationDateBefore(LocalDate expirationDate);

}
