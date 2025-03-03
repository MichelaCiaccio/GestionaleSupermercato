package com.example.supermarket.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.supermarket.entity.User;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {

    User findByOperatorCode(String operatorCode);

    List<User> findByName(String name);

    List<User> findBySurname(String surname);

    List<User> findByRole(String role);
}
