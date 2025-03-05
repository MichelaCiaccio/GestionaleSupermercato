package com.example.supermarket.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Stock {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private int quantity;

    @NotNull
    private LocalDate deliverDate;

    @NotNull
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
