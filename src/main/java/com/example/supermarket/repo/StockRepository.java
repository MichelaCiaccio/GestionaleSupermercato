package com.example.supermarket.repo;

import com.example.supermarket.entity.Stock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findByProduct_NameAndSupplier_Name(String productName,
                                                       String supplierName);

    Stock findByProductId(int productId);

    List<Stock> findByQuantityGreaterThan(int quantity, Pageable pageable);

    List<Stock> findByQuantityLessThan(int quantity, Pageable pageable);

    List<Stock> findByDeliveryDate(LocalDate deliveryDate, Pageable pageable);

    List<Stock> findByExpirationDate(LocalDate expirationDate, Pageable pageable);

    List<Stock> findByExpirationDateBetween(LocalDate startExpirationPeriod,
                                            LocalDate endExpirationPeriod,
                                            Pageable pageable);

}
