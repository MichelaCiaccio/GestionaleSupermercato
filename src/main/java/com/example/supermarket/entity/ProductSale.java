package com.example.supermarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_id", unique = true)
    @JsonIgnore
    private Sale sale;

}
