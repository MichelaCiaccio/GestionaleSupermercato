package com.example.supermarket.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class HashCodeService {

    public String generateOperatorCode(String name, String surname, String email, String role)
            throws NoSuchAlgorithmException {

        // Recupero gli input e il tempo della creazion
        String creationTime = LocalDateTime.now().toString();
        String input = name + surname + role + email + creationTime;

        // Tramite MessageDigest converto gli input in un array di byte
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        // Uso StringBuilder per formattare ogni byte e trasformarlo in una stringa
        StringBuilder operatoCode = new StringBuilder();
        for (byte x : digest) {
            operatoCode.append(String.format("%02x", x));
        }
        return operatoCode.toString();

    }
}
