package com.example.supermarket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.supermarket.entity.User;
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

    @PostMapping("/add")
    public ResponseEntity<?> createNewUser(@Valid @ModelAttribute User user) {

        userService.save(user);
        return ResponseEntity.ok("User save successfully");
    }

    @GetMapping("/all")
    public List<User> getAllUser() throws EntityNotFoundException {
        return userService.findAll();
    }

    @GetMapping("/operatorCode/")
    public User findByOperatorCode(@RequestParam String operatorCode) throws EntityNotFoundException {
        return userService.findByOperatorCode(operatorCode);
    }

    @GetMapping("/name/")
    public List<User> findByName(@RequestParam String name) throws EntityNotFoundException {
        return userService.findByName(name);
    }

    @GetMapping("/surname/")
    public List<User> findBySurname(@RequestParam String surname) throws EntityNotFoundException {
        return userService.findBySurname(surname);

    }

    @GetMapping("/role/")
    public List<User> findByROle(@RequestParam String role) {
        return userService.findByRole(role);
    }

    @DeleteMapping("/all/delete")
    public ResponseEntity<String> deleteAllUser() {
        userService.deleteAll();
        return ResponseEntity.ok("All users have been deleted");
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteByOperatorCode(@RequestParam String operatorCode) {
        userService.deleteByOperatorCode(operatorCode);
        return ResponseEntity.ok("Delete Successfully");
    }

}
