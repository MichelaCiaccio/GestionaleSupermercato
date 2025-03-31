package com.example.supermarket.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Sale {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private double totalPrice;

    
    private double discountPrice;

    @NotNull
    @PastOrPresent
    private LocalDateTime saleDate;

    @OneToMany(mappedBy = "sale")
    private List<ProductSale> productSales;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @Nullable
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    @Nullable
    private Deal deal;

}
