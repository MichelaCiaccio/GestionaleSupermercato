package com.example.supermarket.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.supermarket.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Category findById(int id);
}
