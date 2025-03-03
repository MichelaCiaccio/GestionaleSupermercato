package com.example.supermarket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @NotNull
    private String operatorCode;

    @NotNull(message = "The name is required")
    private String name;

    @NotNull(message = "The surname is required")
    private String surname;

    @NotNull(message = "The email is required")
    private String email;

    @NotNull(message = "The role is required")
    private String role;

    @NotNull(message = "The password is required")
    private String password;

}
