package com.example.supermarket.service;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.entity.Product;
import com.example.supermarket.repo.DiscountRepository;
import com.example.supermarket.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepo;

    @Autowired
    private ProductRepository productRepo;

    /**
     * This method searches for all the discount, organizes them into pagination of 20 elements,
     * and sorts them according
     * to a specified direction and data type.
     * If the page number, sort direction or the data type are not provided by the client,
     * default values are set.
     * Check if any discount exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display.
     * @param sortDirection The direction in which the client wants the products to be ordered.
     *                      Defaults to "ASC" if null or blank.
     * @param dataType      The data by which the discount should be ordered.
     * @return A Page containing the list of discounts.
     */
    public Page<Discount> findAllDiscountSorted(Integer page, String sortDirection,
                                                String dataType) {

        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "active" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Discount> discounts = discountRepo.findAll(pageable);
        if (discounts.isEmpty()) {
            throw new EntityNotFoundException("There are no discounts");
        }
        return discounts;
    }

    /**
     * This method creates a new discount and assigns it to a list of products.
     * This method first saves the discount.
     * Then, for each existing product assigns the saved discount to it.
     * If any product ID does not correspond to an existing
     * and non-removed product, an EntityNotFoundException is thrown.
     *
     * @param discount   The discount entity to be created.
     * @param productIds The list of product IDs to which the discount should be applied.
     */

    public void createNewDiscount(Discount discount, List<Integer> productIds) {
        Discount savedDiscount = discountRepo.save(discount);

        // Per ogni productId, cerca il prodotto e impostagli il discount
        for (Integer productId : productIds) {
            Product product = productRepo.findByIdAndRemovedFalse(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
            product.setDiscount(savedDiscount);
            productRepo.save(product);
        }
    }


    /**
     * This method updates the discount with new data.
     * It removes the discount association from all currently linked products and then
     * assigns the discount to the products identified by the provided product IDs.
     * If any product or the discount is not found, an EntityNotFoundException is thrown.
     *
     * @param discountId    The ID of the new discount.
     * @param modDiscount   The new discount.
     * @param modProductIds The list of product IDs to associate with the new discount.
     */
    public void updateDiscount(Integer discountId, Discount modDiscount,
                               List<Integer> modProductIds) {

        // Trova il discount esistente
        Discount existingDiscount = discountRepo.findById(discountId)
                .orElseThrow(() -> new EntityNotFoundException("Discount with id " + discountId + " not found"));

        // Modifico il discount con i nuove dati
        existingDiscount.setName(modDiscount.getName());
        existingDiscount.setDiscountPercentage(modDiscount.getDiscountPercentage());
        existingDiscount.setEndDate(modDiscount.getEndDate());
        existingDiscount.setActive(modDiscount.isActive());


        // Trovo la lista di prodotti attualmente associati al discount e ne rimuovo l'associazione
        for (Product product : productRepo.findByDiscountId(discountId)) {
            product.setDiscount(null);
            productRepo.save(product);
        }

        // Assegno i nuovi prodotti, se esistono, al discount
        for (Integer productId : modProductIds) {
            Product product = productRepo.findByIdAndRemovedFalse(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
            product.setDiscount(existingDiscount);
            productRepo.save(product);
        }

        discountRepo.save(existingDiscount);
    }

    /**
     * This method is a scheduled task that updates the status of discounts based on their end date.
     * Checks if the discount's end date has passed.
     * If the discount's end date is before today's date, the discount is marked as inactive.
     * It searches for all discounts and then checks if their
     * end date is in the past.
     * If the end date has passed, the discount's 'active' status is set
     * to false.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDiscountStatus() {
        List<Discount> discounts = discountRepo.findAll();
        LocalDate today = LocalDate.now();
        for (Discount discount : discounts) {
            LocalDate expiryDate = discount.getEndDate();
            if (expiryDate.isBefore(today)) {
                discount.setActive(false);
            }
            discountRepo.save(discount);
        }
    }

    public void deleteAll() {
        List<Discount> discounts = discountRepo.findAll();
        if (discounts.isEmpty()) {
            throw new EntityNotFoundException("There are no discount to delete");
        }
        List<Product> currentProducts = productRepo.findByDiscountIn(discounts);
        for (Product product : currentProducts) {
            product.setDiscount(null);
        }
        productRepo.saveAll(currentProducts);
        discountRepo.deleteAll();
    }

    public void deleteById(Integer discountId) {
        Discount existingDiscount =
                discountRepo.findById(discountId).orElseThrow(() -> new EntityNotFoundException(
                        "No discount with this id to delete"));
        List<Product> currentProducts = productRepo.findByDiscountId(discountId);
        for (Product product : currentProducts) {
            product.setDiscount(null);
        }
        productRepo.saveAll(currentProducts);
        discountRepo.deleteById(discountId);
    }
}
