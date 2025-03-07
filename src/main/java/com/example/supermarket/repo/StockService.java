package com.example.supermarket.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.supermarket.entity.Stock;
import java.util.List;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.Supplier;

public interface StockService extends JpaRepository<Stock, Integer> {

    List<Stock> findByProduct(Product product);

    List<Stock> findBySupplier(Supplier supplier);

    List<Stock> findByProductAndSupplier(Product product, Supplier supplier);

    List<Stock> findByQuantityGreaterThan(int quantity);

}
