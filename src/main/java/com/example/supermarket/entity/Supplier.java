package com.example.supermarket.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(name = "stock", joinColumns = @JoinColumn(name = "supplier_id"), inverseJoinColumns =
    @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @OneToMany(mappedBy = "supplier")
    @Nullable
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Set<Stock> stocks;

}
