package com.example.supermarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.SupplierRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepo;

    /**
     * This method searches for stocks using the id as a parameter.
     * If the supplier doesn't exists throw an EntityNotFoundException.
     * Otherwise it return the supplier
     * 
     * @param id the id of the supplier
     * @return the supplier found
     */
    public Supplier findById(int id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " not found"));
        return supplier;
    }
}
