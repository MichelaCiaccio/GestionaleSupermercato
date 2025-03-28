package com.example.supermarket.service;

import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepo;

    /**
     * This method searches for all the sales, organizes them into pagination of 20 elements,
     * and sorts them according
     * to a specified direction.
     * If the client does not provide the page number or sort direction,
     * default values are set.
     * Check if any sale exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display.
     * @param sortDirection The direction in which the client wants the sales to be ordered.
     *                      Defaults to "ASC" if null or blank.
     * @return A Page containing the list of sales.
     */
    public Page<Sale> findAllSalesSorted(Integer page, String sortDirection) {
        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;


        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction));
        Page<Sale> sales = saleRepo.findAll(pageable);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("There are no registered sales");
        }

        return sales;
    }

    /**
     * This method searches for sales by their sale date.
     * If no sales are found, throw an EntityNotFoundException.
     * Otherwise, a list of found sales is returned
     *
     * @param saleDate The date of the sale
     * @return The sales found
     */
    public List<Sale> findBySaleDate(LocalDate saleDate) {
        List<Sale> sales = saleRepo.findBySaleDate(saleDate);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("There are no registered sales on " + saleDate);
        }
        return sales;
    }


}
