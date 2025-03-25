package com.example.supermarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Supplier {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String email;

    // @ManyToMany(mappedBy = "suppliers", fetch = FetchType.LAZY)
    // @Nullable
    //private List<Product> products;


}
