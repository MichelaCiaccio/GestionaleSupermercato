package com.example.supermarket.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.User;
import com.example.supermarket.repo.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByOperatorCode(String operatorCode) {
        if (!userRepository.existsById(operatorCode)) {
            throw new EntityNotFoundException("There are no Users");
        }
        return userRepository.findByOperatorCode(operatorCode);
    }

    public List<User> findByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Users with name " + name + " not found");
        }
        return users;
    }

    public List<User> findBySurname(String surname) {
        List<User> users = userRepository.findBySurname(surname);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Users with surname " + surname + " not found");
        }
        return users;
    }

    public List<User> findByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Users with role " + role + " not found");
        }
        return users;
    }

    public List<User> findAll() {
        Iterable<User> iterableUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();
        iterableUsers.forEach(users::add);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("There are no Users");
        }
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
