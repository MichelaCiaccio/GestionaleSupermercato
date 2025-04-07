package com.example.supermarket.entity;

import com.example.supermarket.entity.enums.DealType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private DealType dealType;

    @NotNull
    private LocalDate endDate;

    private boolean active;

    @OneToMany(mappedBy = "deal")
    private List<Category> categories;


}
