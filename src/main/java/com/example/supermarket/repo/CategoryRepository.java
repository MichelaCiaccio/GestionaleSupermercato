package com.example.supermarket.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.supermarket.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findById(int id);

    Category findByName(String name);

}
