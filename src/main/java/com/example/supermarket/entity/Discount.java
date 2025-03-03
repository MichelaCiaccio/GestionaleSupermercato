package com.example.supermarket.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Discount {

    @Id
    @NotNull
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
    @NotNull
    private Set<Sale> sales;

}
