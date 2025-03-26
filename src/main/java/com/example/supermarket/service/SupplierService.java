package com.example.supermarket.service;

import com.example.supermarket.entity.Supplier;
import com.example.supermarket.repo.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepo;

    public List<Supplier> findAll() {
        List<Supplier> suppliers = supplierRepo.findAll();
        if (suppliers.isEmpty()) {
            throw new EntityNotFoundException("There are no suppliers");
        }
        return suppliers;
    }

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
                .orElseThrow(() -> new EntityNotFoundException("Supplier with name " + name + " " +
                                                                       "not found"));
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
            throw new EntityNotFoundException("There are no supplier with this id" + id + " to " +
                                                      "delete");
        }
        supplierRepo.deleteById(id);
    }

    public Supplier createNewSupplier(@Valid Supplier supplier) {
        Optional<Supplier> existingSupplier =
                supplierRepo.findByName(supplier.getName());
        if (existingSupplier.isEmpty()) {
            Supplier newSupplier = new Supplier();
            newSupplier.setAddress(supplier.getAddress());
            newSupplier.setName(supplier.getName());
            newSupplier.setPhoneNumber(supplier.getPhoneNumber());
            newSupplier.setEmail(supplier.getEmail());
            supplierRepo.save(newSupplier);
            return newSupplier;
        } else {
            return existingSupplier.get();
        }
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

   /* public void update(int id, Supplier modSupplier) {
        Supplier supplier = supplierRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No supplier with id " + id));

        supplier.setName(modSupplier.getName());
        supplier.setEmail(modSupplier.getEmail());
        supplier.setPhoneNumber(modSupplier.getPhoneNumber());
        supplier.setStocks(modSupplier.getStocks());
        supplier.setAddress(modSupplier.getAddress());
        supplier.setProducts(modSupplier.getProducts());
        supplierRepo.save(supplier);
    }*/
}
