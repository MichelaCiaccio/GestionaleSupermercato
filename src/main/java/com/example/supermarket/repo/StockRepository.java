package com.example.supermarket.repo;

import com.example.supermarket.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StockRepository extends JpaRepository<Stock, Integer> {
}
