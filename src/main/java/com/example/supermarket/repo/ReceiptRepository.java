package com.example.supermarket.repo;

import com.example.supermarket.entity.Receipt;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    void deleteBySale_Id(int saleId);

    @Nonnull
    Page<Receipt> findAll(@Nullable Pageable pageable);
}
