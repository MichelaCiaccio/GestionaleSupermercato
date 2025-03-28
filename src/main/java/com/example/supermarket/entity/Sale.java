package com.example.supermarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Sale {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private double totalPrice;

    @NotNull
    private double discountPrice;

    @NotNull
    private LocalDateTime saleDate;

    @OneToMany(mappedBy = "sale")
    private List<ProductSale> productSales;

    @OneToOne(mappedBy = "sale")
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

}
