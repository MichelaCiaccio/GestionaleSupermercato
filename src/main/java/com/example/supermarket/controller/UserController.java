package com.example.supermarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.supermarket.entity.User;
import com.example.supermarket.repo.UserRepository;
import com.example.supermarket.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> createNewUser(@Valid @ModelAttribute User user) {

        userService.save(user);
        return ResponseEntity.ok("User save successfully");
    }

    @GetMapping("/all")
    public Iterable<User> getAllUser() {
        return userService.findAll();

    }

    @GetMapping("/name/")
    public List<User> findByName(@RequestParam String name) {
        List<User> users = userService.findByName(name);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Users with name " + name + " not found");
        }
        return users;
    }

    @GetMapping("/operatorCode/")
    public User findByOperatorCode(@RequestParam String operatorCode) {
        if (!userRepository.existsById(operatorCode)) {
            throw new EntityNotFoundException("User with Operator Code: " + operatorCode + " not found");
        }

        return userService.findByOperatorCode(operatorCode);
    }

    @GetMapping("/surname/")
    public List<User> findBySurname(@RequestParam String surname) {
        List<User> users = userService.findBySurname(surname);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Users with surname " + surname + " not found");
        }
        return users;
    }

    @GetMapping("/role/")
    public List<User> findByROle(@RequestParam String role) {
        List<User> users = userService.findByRole(role);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Users with role " + role + " not found");
        }
        return users;
    }

    @DeleteMapping("/all/delete")
    public ResponseEntity<String> deleteAllUser() {
        if (userRepository.count() > 0) {
            userService.deleteAll();
            return ResponseEntity.ok("All Users have been deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There's no Users");
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteByOperatorCode(@RequestParam String operatorCode) {
        if (userRepository.existsById(operatorCode)) {
            userService.deleteByOperatorCode(operatorCode);
            return ResponseEntity.ok("Delete Successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

    }

}
