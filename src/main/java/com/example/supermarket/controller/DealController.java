package com.example.supermarket.controller;

import com.example.supermarket.entity.Deal;
import com.example.supermarket.service.DealService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/deals")
public class DealController {

    @Autowired
    private DealService dealServ;

    @GetMapping("")
    public Page<Deal> getAllDeal(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) String sortDirection,
                                 @RequestParam(required = false) String dataType) throws EntityNotFoundException {
        return dealServ.findAllDealSorted(page, sortDirection, dataType);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDeal(@Valid @RequestBody Deal deal) {
        dealServ.createDeal(deal);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deal created successfully");
    }
}
