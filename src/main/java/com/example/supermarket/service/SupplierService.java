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
     * This method searches for supplier using the id as a parameter.
     * If the supplier doesn't exists throw an EntityNotFoundException.
     * 
     * @param id the id of the supplier
     * @return the supplier found
     */
    public Supplier findById(int id) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " not found"));
        return supplier;
    }

    /**
     * This method searches for supplier using the name as a parameter.
     * If the supplier doeasn't exists throw an EntityNotFoundException.
     * 
     * @param name the name of the supplier
     * @return the supplier found
     */
    public Supplier findByName(String name) {
        Supplier supplier = supplierRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Supplier with name " + name + " not found"));
        return supplier;
    }
}
