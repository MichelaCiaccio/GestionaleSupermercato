package com.example.supermarket.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> findAll() {
        Iterable<User> iterableUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();
        iterableUsers.forEach(users::add);
        users.sort(Comparator.comparing(o -> o.getRole()));
        return users;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteByOperatorCode(String operatorCode) {
        userRepository.deleteById(operatorCode);
    }

}
