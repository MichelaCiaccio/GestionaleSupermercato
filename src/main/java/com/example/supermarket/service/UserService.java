package com.example.supermarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.User;
import com.example.supermarket.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByOperatorCode(String operatorCode) {
        return userRepository.findByOperatorCode(operatorCode);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll(Sort.by("role"));
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteByOperatorCode(String operatorCode) {
        userRepository.deleteById(operatorCode);
    }

}
