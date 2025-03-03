package com.example.supermarket.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.supermarket.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
