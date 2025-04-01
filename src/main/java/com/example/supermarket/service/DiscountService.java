package com.example.supermarket.service;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.repo.DiscountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepo;

    public Page<Discount> findAllDiscountSorted(Integer page, String sortDirection, String dataType,
                                                boolean showRemoved) {

        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "name" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Discount> discounts = showRemoved ? discountRepo.findAll(pageable) :
                discountRepo.findByRemovedFalse(pageable);
        if (discounts.isEmpty()) {
            throw new EntityNotFoundException("There are no discounts");
        }
        return discounts;
    }
}
