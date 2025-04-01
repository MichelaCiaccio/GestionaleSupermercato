package com.example.supermarket.repo;

import com.example.supermarket.entity.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Page<Discount> findByRemovedFalse(Pageable pageable);
}
