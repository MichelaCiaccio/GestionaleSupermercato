package com.example.supermarket.entity;

import java.util.Set;

import com.example.supermarket.entity.enums.DealType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Deal {

    @Id
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private DealType dealType;

    @NotNull
    private int duration;

    @NotNull
    private boolean active;

    @OneToMany(mappedBy = "deal")
    @NotNull
    private Set<Sale> sales;

}
