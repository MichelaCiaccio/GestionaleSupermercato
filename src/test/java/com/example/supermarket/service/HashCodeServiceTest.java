package com.example.supermarket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HashCodeServiceTest {

    @Mock
    HashCodeService hashCodeService;

    @Test
    void testGenerateOperatorCode() throws NoSuchAlgorithmException {

        // GIVEN
        String name = "Nome";
        String surname = "Cognome";
        String email = "email@test.com";
        String role = "ruolo";

        // WHEN
        String expectCode = "07004cc9841475c848a747e4e5e3f6953befa5b8";
        when(hashCodeService.generateOperatorCode(name, surname, email, role)).thenReturn(expectCode);
        String operatorCode = hashCodeService.generateOperatorCode(name, surname, email, role);

        // VERIFY
        assertNotNull(operatorCode);
        assertEquals(40, operatorCode.length());
        verify(hashCodeService, times(1)).generateOperatorCode(name, surname, email, role);
    }

}
