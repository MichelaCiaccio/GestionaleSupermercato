package com.example.supermarket.repo;

import com.example.supermarket.entity.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Integer> {
}
