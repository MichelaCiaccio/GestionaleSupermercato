package com.example.supermarket.entity;

import com.example.supermarket.entity.enums.DealType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private List<Sale> sales;

}
