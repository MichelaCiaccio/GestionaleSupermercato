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
