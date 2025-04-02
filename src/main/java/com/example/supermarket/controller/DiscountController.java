package com.example.supermarket.controller;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountServ;

    @GetMapping("")
    public Page<Discount> getAllDiscounts(@RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) String sortDirection,
                                          @RequestParam(required = false) String dataType) throws EntityNotFoundException {
        return discountServ.findAllDiscountSorted(page, sortDirection, dataType);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDiscount(@Valid @RequestBody Discount discount,
                                              @RequestParam List<Integer> productIds) {
        discountServ.createNewDiscount(discount, productIds);
        return ResponseEntity.status(HttpStatus.CREATED).body("Discount " + discount.getName() +
                                                                      " created successfully");
    }
}
