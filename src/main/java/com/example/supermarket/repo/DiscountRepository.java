package com.example.supermarket.repo;

import com.example.supermarket.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    List<Discount> findByActiveTrue();

}
