package com.example.supermarket.controller;

import com.example.supermarket.DTO.ReceiptDTO;
import com.example.supermarket.service.ReceiptService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptServ;

    @GetMapping("")
    public Page<ReceiptDTO> getAll(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) String sortDirection) throws EntityNotFoundException {
        return receiptServ.findAllReceiptSorted(page, sortDirection);
    }
}
