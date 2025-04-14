package com.example.supermarket.service;

import com.example.supermarket.DTO.Mapper.ReceiptMapper;
import com.example.supermarket.DTO.ReceiptDTO;
import com.example.supermarket.DTO.SaleDTO;
import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.Receipt;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepo;

    @Mock
    private ReceiptMapper receiptMapper;

    @InjectMocks
    private ReceiptService receiptServ;

    @Test
    void createNewReceipt() {
    }

    @Test
    void createReceiptCode() {
    }

    @Test
    void findAllReceiptSorted() {

        // Given
        List<ProductSale> productSales = new ArrayList<>();
        Sale sale = new Sale(1, BigDecimal.valueOf(50), BigDecimal.valueOf(30),
                             LocalDateTime.now(), productSales, null);
        Receipt receipt = new Receipt(1, "Receipt Code", sale);
        ReceiptDTO receiptDTO = new ReceiptDTO("Receipt Code", new SaleDTO());
        List<Receipt> receiptList = List.of(receipt);
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "sale.saleDate"
                                                         ));
        Page<Receipt> receiptPage = new PageImpl<>(receiptList, pageable, receiptList.size());

        // When
        when(receiptRepo.findAll(pageable)).thenReturn(receiptPage);
        when(receiptMapper.toReceiptDTO(receipt)).thenReturn(receiptDTO);
        Page<ReceiptDTO> ret = receiptServ.findAllReceiptSorted(0, "ASC");

        // Verify
        verify(receiptRepo, times(1)).findAll(pageable);
        assertEquals(1, ret.getContent().size());
        assertEquals(receiptDTO, ret.getContent().get(0));
    }
}