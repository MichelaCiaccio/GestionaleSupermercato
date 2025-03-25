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

    @NotNull(message = "The supplier name is required")
    @Column(unique = true)
    private String name;

    @NotNull(message = "The supplier address is required")
    private String address;

    @NotNull(message = "The supplier phone number is required")
    private String phoneNumber;

    @NotNull(message = "The supplier email is required")
    private String email;

    // @ManyToMany(mappedBy = "suppliers", fetch = FetchType.LAZY)
    // @Nullable
    //private List<Product> products;

    //@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, orphanRemoval = true)
    //@NotNull
    //private List<Stock> stocks;


}
