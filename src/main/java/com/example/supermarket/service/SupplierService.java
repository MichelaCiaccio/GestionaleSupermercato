package com.example.supermarket.service;

import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return supplierRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " not found"));
    }

    /**
     * This method searches for supplier using the name as a parameter.
     * If the supplier doesn't exists throw an EntityNotFoundException.
     *
     * @param name the name of the supplier
     * @return the supplier found
     */
    public Supplier findByName(String name) {
        Supplier supplier = supplierRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Supplier with name " + name + " not found"));
        return supplier;
    }

    public List<Supplier> findByProductName(String name) {
        List<Supplier> suppliers = supplierRepo.findByStocksProductName(name);
        if (suppliers.isEmpty()) {
            throw new EntityNotFoundException("Suppliers providing the product " + name + " not found");
        }
        return suppliers;
    }

    /**
     * This method delete all the suppliers.
     * If there are no suppliers it throws an EntityNotFoundException.
     * Otherwise calls supplierRepository.deleteAll to delete all the suppliers.
     */
    public void deleteAll() {
        if (supplierRepo.findAll().isEmpty()) {
            throw new EntityNotFoundException("There are no suppliers to delete");
        }
        supplierRepo.deleteAll();
    }

    /**
     * This method deletes a supplier identified by its id.
     * Check if the supplier exists and, if it does, proceed to call
     * supplierRepository.deleteById to delete it.
     * Otherwise it throws an EntityNotFoundException.
     *
     * @param id
     */
    public void deleteById(int id) {
        if (supplierRepo.findById(id).isEmpty()) {
            throw new EntityNotFoundException("There are no supplier with this id" + id + " to delete");
        }
        supplierRepo.deleteById(id);
    }

    /**
     * This method creates a new supplier.
     * It checks if the supplier already exists, searching by its name, and if it
     * does, throws a
     * DuplicateKeyException.
     * Otherwise calls the supplierRepository.save method to create the new
     * supplier.
     *
     * @param supplier
     */
    public void save(Supplier supplier) {
        if (supplierRepo.findByName(supplier.getName()).isPresent()) {
            throw new DuplicateKeyException("Supplier with name " + supplier.getName() + " already exists");
        }
        supplierRepo.save(supplier);
    }

    /**
     * This method updates a supplier identified by its ID.
     * Checks if the supplier exists, and if it doesn't, throws an
     * EntityNotFoundException.
     * Otherwise, it proceeds to update the supplier's attribute with the new
     * information and saves the modified supplier.
     *
     * @param id          The ID of the supplier to be updated.
     * @param modSupplier The new supplier data to update with.
     */

    public void update(int id, Supplier modSupplier) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No supplier with id " + id));

        supplier.setName(modSupplier.getName());
        supplier.setEmail(modSupplier.getEmail());
        supplier.setPhoneNumber(modSupplier.getPhoneNumber());
        supplier.setStocks(modSupplier.getStocks());
        supplier.setAddress(modSupplier.getAddress());
        supplierRepo.save(supplier);
    }
}
