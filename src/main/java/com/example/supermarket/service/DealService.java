package com.example.supermarket.service;

import com.example.supermarket.entity.Deal;
import com.example.supermarket.repo.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DealService {

    @Autowired
    private DealRepository dealRepo;

    /**
     * This method searches for all the deal, organizes them into pagination of 20 elements,
     * and sorts them according
     * to a specified direction and data type.
     * If the page number, sort direction or the data type are not provided by the client,
     * default values are set.
     * Check if any deal exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display.
     * @param sortDirection The direction in which the client wants the products to be ordered.
     *                      Defaults to "ASC" if null or blank.
     * @param dataType      The data by which the deal should be ordered.
     * @return A Page containing the list of deal.
     */
    public Page<Deal> findAllDealSorted(Integer page, String sortDirection,
                                        String dataType) {

        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "active" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Deal> deals = dealRepo.findAll(pageable);
        if (deals.isEmpty()) {
            throw new EntityNotFoundException("There are no discounts");
        }
        return deals;
    }


}
