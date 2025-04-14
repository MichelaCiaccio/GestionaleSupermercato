package com.example.supermarket.service;

import com.example.supermarket.DTO.Mapper.ReceiptMapper;
import com.example.supermarket.DTO.ReceiptDTO;
import com.example.supermarket.entity.Receipt;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.ReceiptRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    /**
     * Creates a new receipt associated with the given sale and saves it.
     * This method generates a unique receipt code using the details of the sale
     * (sale date, total price,
     * and the number of products in the sale).
     * It then creates a new receipt, sets the receipt
     * code, associates the receipt with the given sale, and saves it to the repository.
     *
     * @param sale The new sale
     */
    public void createNewReceipt(Sale sale) {
        Receipt receipt = new Receipt();
        String input =
                sale.getSaleDate().toString() + sale.getTotalPrice() + sale.getProductSales().size();
        receipt.setReceiptCode(this.createReceiptCode(input));

        receipt.setSale(sale);
        receiptRepository.save(receipt);
    }

    /**
     * Generates an SHA-1 hash-based receipt code from the given input string.
     * This method takes an input string, applies the SHA-1 hashing algorithm, and returns a
     * hexadecimal
     * string representation of the hash, which is used as a unique receipt code.
     *
     * @param input The input string to be hashed
     * @return The generated receipt code
     */
    public String createReceiptCode(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not available", e);
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }


    /**
     * Retrieves a paginated and sorted list of Receipt.
     * If no receipts are found, it throws an EntityNotFoundException.
     *
     * @param page          The page number to retrieve
     * @param sortDirection The sorting direction
     * @return a Page of Receipt sorted by sale date
     */
    public Page<ReceiptDTO> findAllReceiptSorted(Integer page, String sortDirection) {
        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;


        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, "sale.saleDate"));
        Page<Receipt> receipts = receiptRepository.findAll(pageable);
        if (receipts.isEmpty()) {
            throw new EntityNotFoundException("There are no receipts");
        }

        return receipts.map(receiptMapper::toReceiptDTO);
    }

}
