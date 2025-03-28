package com.example.supermarket.service;

import com.example.supermarket.entity.Receipt;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

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
        try {
            receipt.setReceiptCode(this.createReceiptCode(input));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nel generare il codice scontrino", e);
        }
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
    public String createReceiptCode(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

}
