package com.example.supermarket.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    
    private BigDecimal totalPrice;


    private BigDecimal discountPrice;

    @NotNull
    @PastOrPresent
    private LocalDateTime saleDate;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<ProductSale> productSales;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    @Nullable
    private Deal deal;

}
