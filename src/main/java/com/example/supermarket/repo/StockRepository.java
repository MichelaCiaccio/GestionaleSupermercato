package com.example.supermarket.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.supermarket.entity.Stock;
import java.util.List;

import java.time.LocalDate;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

        List<Stock> findByProductName(String name, Pageable pageable);

        Stock findByProductId(int productId);

        List<Stock> findBySupplierName(String name, Pageable pageable);

        List<Stock> findByQuantityGreaterThan(int quantity, Pageable pageable);

        List<Stock> findByQuantityLessThan(int quantity, Pageable pageable);

        List<Stock> findByDeliveryDate(LocalDate deliveryDate, Pageable pageable);

        List<Stock> findByExpirationDate(LocalDate expirationDate, Pageable pageable);

        List<Stock> findByExpirationDateBetween(LocalDate startExpirationPeriod, LocalDate endExpirationPeriod,
                        Pageable pageable);

        boolean existsByProductNameAndSupplierNameAndExpirationDate(String productName, String supplierName,
                        LocalDate expirationDate);

}
