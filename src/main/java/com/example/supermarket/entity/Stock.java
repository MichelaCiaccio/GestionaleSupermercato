package com.example.supermarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private int quantity;

    @NotNull
    @PastOrPresent
    private LocalDate deliveryDate;

    @NotNull
    @Future
    private LocalDate expirationDate;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;


}
