package com.example.supermarket.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    private double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Nullable
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "product")
    @NotNull
    private Set<Stock> stocks;

}
