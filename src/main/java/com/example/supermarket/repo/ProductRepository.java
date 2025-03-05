package com.example.supermarket.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.supermarket.entity.Product;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findById(Integer id);

    List<Product> findByName(String name);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findBySellingPrice(double sellingPrice);

    @Query("select p from Product p join p.stocks s join s.supplier sup where sup.name= :supplierName")
    List<Product> findBySupplierName(@Param("supplierName") String supplierName);

    @Query("select p from Product p join p.stocks s where s.expirationDate = :expirationDate")
    List<Product> findByStocks_ExpirationDate(LocalDate expirationDate);

    @Query("select p from Product p join p.stocks s where s.quantity = :quantity")
    List<Product> findByStock_Quantity(int quantity);

}
