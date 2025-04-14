package com.example.supermarket.service;

import com.example.supermarket.DTO.DealDTO;
import com.example.supermarket.DTO.Mapper.DealMapper;
import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.DealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @InjectMocks
    private DealService dealService;

    @Mock
    private DealRepository dealRepo;

    @Mock
    private CategoryRepository categoryRepo;

    @Mock
    private DealMapper dealMapper;


    @Test
    void findAllDealSorted() {

        // Given
        Category category = new Category(1, "Category1");
        Deal deal = new Deal(1, "Deal1", null, LocalDate.now().plusDays(10), true,
                             List.of(category));
        DealDTO dealDTO = new DealDTO("Deal1", null, LocalDate.now().plusDays(10), true);
        List<Deal> dealList = List.of(deal);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "active"));
        Page<Deal> dealPage = new PageImpl<>(dealList, pageable, dealList.size());

        // When
        when(dealRepo.findAll(pageable)).thenReturn(dealPage);
        when(dealMapper.toDealDTO(deal)).thenReturn(dealDTO);
        Page<DealDTO> ret = dealService.findAllDealSorted(0, "ASC", "active");

        // Verify
        assertEquals(1, ret.getContent().size());  // Verifica che ci sia 1 deal nel contenuto
        assertEquals(dealDTO, ret.getContent().get(0));  // Verifica che l'elemento sia il nostro
        // dealDTO
    }


    @Test
    void updateDealStatus() {
    }

    @Test
    void createDeal() {
    }
}