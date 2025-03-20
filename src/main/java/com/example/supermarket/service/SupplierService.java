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
     * This method searches for a supplier by its ID.
     * If the supplier does not exist, throw an EntityNotFoundException.
     * Otherwise, return the found supplier;
     *
     * @param id The ID of the supplier
     * @return The supplier found
     */
    public Supplier findById(int id) {
        return supplierRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier with id " + id + " not found"));
    }

    /**
     * This method searches for supplier by its name.
     * If the supplier does not exist, throw an EntityNotFoundException.
     * Otherwise, return the found supplier.
     *
     * @param name The name of the supplier
     * @return The supplier found
     */
    public Supplier findByName(String name) {
        return supplierRepo.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Supplier with name " + name + " not found"));
    }

    /**
     * This method searches for suppliers who supply a product identified by its name.
     * If no suppliers are found, throw an EntityNotFoundException.
     * Otherwise, returns the list of found suppliers
     *
     * @param productName The name of the product
     * @return A list of found suppliers.
     */
    public List<Supplier> findByProductName(String productName) {
        List<Supplier> suppliers = supplierRepo.findByProducts_Name(productName);
        if (suppliers.isEmpty()) {
            throw new EntityNotFoundException("Suppliers providing the product " + productName + " not found");
        }
        return suppliers;
    }

    /**
     * This method deletes all the suppliers.
     * If there are no suppliers, it throws an EntityNotFoundException.
     * Otherwise, call supplierRepository.deleteAll to delete all the suppliers.
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
     * Otherwise, it throws an EntityNotFoundException.
     *
     * @param id The id of the supplier
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
     * does, throws a DuplicateKeyException.
     * Otherwise, proceed to create the new supplier.
     *
     * @param supplier The supplier to be saved
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
        supplier.setProducts(modSupplier.getProducts());
        supplierRepo.save(supplier);
    }
}
