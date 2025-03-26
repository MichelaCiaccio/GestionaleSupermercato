package com.example.supermarket.controller;

import com.example.supermarket.entity.Supplier;
import com.example.supermarket.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierServ;

    @GetMapping("")
    public List<Supplier> getAll() {
        return supplierServ.findAll();
    }
}
