package com.example.supermarket.service;

import com.example.supermarket.DTO.Mapper.SaleMapper;
import com.example.supermarket.DTO.SaleDTO;
import com.example.supermarket.deals.BUY3PAY2Deal;
import com.example.supermarket.deals.DISCOUNTONTOTALDeal;
import com.example.supermarket.deals.DealStrategy;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.Sale;
import com.example.supermarket.repo.ReceiptRepository;
import com.example.supermarket.repo.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepo;

    @Autowired
    private ReceiptRepository receiptRepo;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private DealService dealServ;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private StockService stockServ;

    /**
     * This method searches for all the sales, organizes them into pagination of 20 elements,
     * and sorts them according
     * to a specified direction.
     * If the client does not provide the page number or sort direction,
     * default values are set.
     * Check if any sale exists and return them.
     * Otherwise, it throws and EntityNotFoundException
     *
     * @param page          The page number the client wants to display.
     * @param sortDirection The direction in which the client wants the sales to be ordered.
     *                      Defaults to "ASC" if null or blank.
     * @return A Page containing the list of sales.
     */
    public Page<SaleDTO> findAllSalesSorted(Integer page, String sortDirection, String dataType) {
        page = page == null ? 0 : page;

        sortDirection = sortDirection == null || sortDirection.isBlank() ? "ASC" : sortDirection;

        dataType = dataType == null || dataType.isBlank() ? "saleDate" : dataType;


        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, 20, Sort.by(direction, dataType));
        Page<Sale> sales = saleRepo.findAll(pageable);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("There are no registered sales");
        }

        return sales.map(saleMapper::toSaleDto);
    }

    /**
     * This method searches for sales by their sale date.
     * If no sales are found, throw an EntityNotFoundException.
     * Otherwise, a list of found sales is returned
     *
     * @param saleDate The date of the sale
     * @return The sales found
     */
    public List<Sale> findBySaleDate(LocalDateTime saleDate) {
        List<Sale> sales = saleRepo.findBySaleDate(saleDate);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("There are no registered sales on " + saleDate);
        }
        return sales;
    }

    /**
     * This method searches for sales that include a specific product.
     * If no sales are found, throw an EntityNotFoundException.
     * Otherwise, a list of found sales is returned
     *
     * @param productName The name of the product
     * @return The sales found
     */
    public List<Sale> findByProduct(String productName) {
        List<Sale> sales = saleRepo.findByProductSales_Product_Name(productName);
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("There no sales for product " + productName);
        }
        return sales;
    }

    /**
     * This method processes and creates a new sale, updates product stock quantities, and
     * generates a receipt.
     * It updates the stock quantities by subtracting the quantities of the products sold.
     * The method calculates the total price of the sale based on the products' selling
     * prices and their quantities.
     * If discounted prices are available, the discount price is
     * also calculated.
     * The sale is saved and a corresponding receipt is generated.
     *
     * @param sale The new sale.
     */
    @Transactional
    public void createNewSale(Sale sale) {

        List<ProductSale> productSales = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal discountPrice = BigDecimal.ZERO;

        for (ProductSale productSale : sale.getProductSales()) {

            // Verifico se il prodotto è nullo
            if (productSale.getProduct() == null) {
                throw new EntityNotFoundException("A product is required");
            }

            // Imposto prodotti e vendita
            productSale.setProduct(productSale.getProduct());
            productSale.setSale(sale);
            productSale.setQuantity(productSale.getQuantity());
            productSales.add(productSale);

            // Sottrai la quantità dei prodotti venduti nello stock
            stockServ.subStockQuantity(productSale.getProduct().getId(), productSale.getQuantity());

            // Aggiungo il prezzo totale
            totalPrice =
                    totalPrice.add(productSale.getProduct().getSellingPrice().multiply(BigDecimal.valueOf(productSale.getQuantity())));

            // Aggiungi il prezzo scontato se disponibile
            if (productSale.getProduct().getDiscountedSellingPrice() != null) {
                discountPrice =
                        discountPrice.add(productSale.getProduct().getDiscountedSellingPrice().multiply(BigDecimal.valueOf(productSale.getQuantity())));
            }
        }

Ad         // Applico la logica della promozione se esiste
        DealStrategy dealStrategy = this.getDealStrategy(sale.getDeal());
        discountPrice = (dealStrategy != null) ?
                discountPrice.subtract(dealStrategy.applyDeal(sale.getProductSales(),
                                                              sale.getDeal())) : discountPrice;


        // Imposto il prezzo totale della vendita
        sale.setTotalPrice(totalPrice);
        sale.setDiscountPrice(discountPrice);
        sale.setProductSales(productSales);

        //Salvo la vendita e creo la ricevuta
        Sale newSale = saleRepo.save(sale);
        receiptService.createNewReceipt(newSale);
    }

    /**
     * This method deletes all the sales.
     * Check if any sale exists if it doesn't throw an EntityNotFoundException.
     * Otherwise, proceed to delete all the sales.
     */
    public void deleteAll() {
        List<Sale> sales = saleRepo.findAll();
        if (sales.isEmpty()) {
            throw new EntityNotFoundException("There are no sales to delete");
        }
        receiptRepo.deleteAll();
        saleRepo.deleteAll();
    }

    /**
     * This method delete a sale identified by its id.
     * If the sale doesn't exist, an EntityNotFoundException is thrown.
     * Otherwise, it deletes both the sale and the associated receipt.
     *
     * @param id The ID of the sale to delete
     */
    @Transactional
    public void deleteByID(int id) {
        Sale sale = saleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No sale" +
                                                                                                " " +
                                                                                                "with " +
                                                                                                "id " + id + " to delete"));
        receiptRepo.deleteBySale_Id(id);
        saleRepo.deleteById(id);
    }

    public DealStrategy getDealStrategy(Deal deal) {
        switch (deal.getDealType()) {
            case BUY3PAY2 -> {
                return new BUY3PAY2Deal();
            }
            case DISCOUNTONTOTAL -> {
                return new DISCOUNTONTOTALDeal();
            }
            default -> {
                return null;
            }
        }
    }
}
