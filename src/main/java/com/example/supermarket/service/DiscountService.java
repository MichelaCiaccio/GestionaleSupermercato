package com.example.supermarket.service;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.entity.Product;
import com.example.supermarket.repo.DiscountRepository;
import com.example.supermarket.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public Page<Discount> findAllDiscountSorted(Integer page, String sortDirection, String dataType,
                                                boolean showRemoved) {

        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "active" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Discount> discounts = showRemoved ? discountRepo.findAll(pageable) :
                discountRepo.findByRemovedFalse(pageable);
        if (discounts.isEmpty()) {
            throw new EntityNotFoundException("There are no discounts");
        }
        return discounts;
    }

    /**
     * Creates a new discount and assigns it to a list of products.
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
            Product product = productRepo.findByIdAndRemoveFalse(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));
            product.setDiscount(savedDiscount);
            productRepo.save(product);
        }
    }
}
