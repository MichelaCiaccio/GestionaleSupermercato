package com.example.supermarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private int discountPercentage;

    @NotNull
    private int duration;

    @NotNull
    private boolean active;

    @OneToMany(mappedBy = "discount")
    private List<Sale> sales;

}
