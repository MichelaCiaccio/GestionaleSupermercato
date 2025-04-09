package com.example.supermarket.service;

import com.example.supermarket.DTO.DealDTO;
import com.example.supermarket.DTO.Mapper.DealMapper;
import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.repo.CategoryRepository;
import com.example.supermarket.repo.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DealService {

    @Autowired
    private DealRepository dealRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private DealMapper dealMapper;

    /**
     * This method searches for all the deal, organizes them into pagination of 20 elements,
     * and sorts them according
     * to a specified direction and data type.
     * If the page number, sort direction or the data type are not provided by the client,
     * default values are set.
     * Check if any deal exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display.
     * @param sortDirection The direction in which the client wants the products to be ordered.
     *                      Defaults to "ASC" if null or blank.
     * @param dataType      The data by which the deal should be ordered.
     * @return A Page containing the list of deal.
     */
    public Page<DealDTO> findAllDealSorted(Integer page, String sortDirection,
                                           String dataType) {

        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "active" : dataType;

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Deal> deals = dealRepo.findAll(pageable);
        if (deals.isEmpty()) {
            throw new EntityNotFoundException("There are no discounts");
        }
        return deals.map(dealMapper::toDealDTO);
    }

    /**
     * This method is a scheduled task that updates the status of deal based on their end date.
     * Checks if the deal's end date has passed.
     * If the deal's end date is before today's date, the discount is marked as inactive.
     * It searches for all deals and then checks if their
     * end date is in the past.
     * If the end date has passed, the deal's 'active' status is set
     * to false.
     */
    @Scheduled(cron = "0 0 0 * * ? ", zone = "Europe/Rome")
    public void updateDealStatus() {
        List<Deal> deals = dealRepo.findAll();
        LocalDate today = LocalDate.now();
        for (Deal deal : deals) {
            LocalDate expiryDate = deal.getEndDate();
            if (expiryDate.isBefore(today)) {
                deal.setActive(false);
            }
            dealRepo.save(deal);
        }
    }

    /**
     * Creates and saves a new Deal
     * This method retrieves the list of category associated with the deal
     * based on their IDs, and sets them into the deal before saving it.
     *
     * @param deal The deal to be saved
     */
    public void createDeal(Deal deal) {
        List<Integer> categoryIds = deal.getCategories().stream()
                .map(Category::getId)
                .toList();

        // Recuperi le categorie
        List<Category> categories = categoryRepo.findByIdIn(categoryIds);

        // Impostiamo il deal per ciascuna categoria
        deal.setCategories(categories);
        dealRepo.save(deal);
    }
}
