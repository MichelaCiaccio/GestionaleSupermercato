package com.example.supermarket.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "The name is required")
    private String name;

    @NotNull(message = "The selling price is required")
    private BigDecimal sellingPrice;

    private boolean removed;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<Stock> stocks;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @Nullable
    private Discount discount;


}
