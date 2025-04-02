package com.example.supermarket.controller;

import com.example.supermarket.entity.Discount;
import com.example.supermarket.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
