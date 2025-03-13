package com.example.supermarket.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.supermarket.entity.Supplier;

@Repository
@Transactional
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Supplier findByName(String name);

    List<Supplier> findByStocksProductName(String name);
}
