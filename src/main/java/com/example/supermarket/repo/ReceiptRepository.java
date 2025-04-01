package com.example.supermarket.repo;

import com.example.supermarket.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    void deleteBySale_Id(int saleId);
}
