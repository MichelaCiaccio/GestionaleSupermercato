package com.example.supermarket.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    private Set<ProductSale> productSales;

    @OneToOne(mappedBy = "sale")
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

}
