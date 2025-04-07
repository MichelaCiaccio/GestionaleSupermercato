package com.example.supermarket.service;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.enums.DealType;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class DealService {

    @Autowired
    private DealRepository dealRepo;

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
    public Page<Deal> findAllDealSorted(Integer page, String sortDirection,
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
        return deals;
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
     * This method applies the BUY3PAY2 deal.
     * It selects the products that belong to the categories associated with the deal,
     * calculates the number of valid groups of 3 products,
     * and returns the free products from those groups.
     * If the deal type is BUY3PAY2 (THREEPAYTWO), and there are at least 3 products eligible
     * for the promotion, it will calculate how many groups of 3 products can be formed. The
     * least expensive products from each group will be selected and returned.
     *
     * @param productSales A list of ProductSale objects representing the products sold in the sale.
     * @param deal         The promotion deal to be applied, which contains the promotion type
     *                     and categories.
     * @return A list of the least expensive products from the valid groups of 3 eligible products
     * based on the promotion.
     */
    public BigDecimal applyBUY3PAY2Deal(List<ProductSale> productSales, Deal deal) {

        if (deal == null) {
            return BigDecimal.ZERO;
        }
        // Recuperare la categoria a cui fa riferimento la promozione
        List<Category> targetCategories = deal.getCategory();

        // Recupero i prodotti che hanno la stessa categoria di quelle in promozione
        List<Product> targetProducts =
                productSales.stream().map(ProductSale::getProduct).filter(product -> targetCategories.stream()
                        .anyMatch(category -> category.equals(product.getCategory()))).toList();

        // Se il deal è THREEPAYTWO e i prodotti validi sono 3 o più, conto quanti gruppi da 3
        // prodotti esistono
        int groupProducts =
                (deal.getDealType() == DealType.BUY3PAY2 && targetProducts.size() >= 3) ?
                        targetProducts.size() / 3 : 0;

        List<Product> freeProducts =
                targetProducts.stream().sorted(Comparator.comparing(Product::getSellingPrice)).limit(groupProducts).toList();

        // Restituisco i prodotti meno cari in basi al numero di gruppi da 3
        return freeProducts.stream().map(Product::getSellingPrice).reduce(BigDecimal.ZERO,
                                                                          BigDecimal::add);
    }
}
