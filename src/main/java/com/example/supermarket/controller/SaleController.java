package com.example.supermarket.controller;

import com.example.supermarket.DTO.SaleDTO;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/sales")
public class SaleController {

    @Autowired
    private SaleService saleServ;

    @GetMapping("")
    public Page<SaleDTO> getAll(@RequestParam(required = false) Integer page,
                                @RequestParam(required = false) String sortDirection,
                                @RequestParam(required = false) String dataType) throws EntityNotFoundException {
        return saleServ.findAllSalesSorted(page, sortDirection, dataType);
    }

    @GetMapping("/saleDate")
    public List<Sale> getBySaleDate(@RequestParam LocalDateTime saleDate) {
        return saleServ.findBySaleDate(saleDate);
    }

    @GetMapping("/product")
    public List<Sale> getByProduct(@RequestParam String productName) {
        return saleServ.findByProduct(productName);
    }

    @PostMapping("/new")
    public ResponseEntity<String> recordSale(@Valid @RequestBody Sale sale) {
        try {
            saleServ.createNewSale(sale);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sale recorded successfully");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body((e.getMessage()));
        }
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAll() {
        saleServ.deleteAll();

        return ResponseEntity.ok("All sales deleted successfully");
    }

}
