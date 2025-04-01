package com.example.supermarket.service;

import com.example.supermarket.DTO.Mapper.SaleMapper;
import com.example.supermarket.DTO.SaleDTO;
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
     * Creates a new sale, updates stock quantities, and generates a receipt for the sale.
     * This method processes the product sales associated with the given sale,
     * setting the quantity, associating the sale and product, and updating the
     * stock quantities by subtracting the quantities of the products sold.
     * Once the product sales are processed, the sale is saved to the repository
     * and a receipt is generated for the sale.
     *
     * @param sale The new sale
     */
    @Transactional
    public void createNewSale(Sale sale) {
        List<ProductSale> productSales = new ArrayList<>();
        for (ProductSale productSale : sale.getProductSales()) {
            productSale.setProduct(productSale.getProduct());
            productSale.setSale(sale);
            productSale.setQuantity(productSale.getQuantity());
            productSales.add(productSale);
            if (productSale.getProduct() == null) {
                throw new EntityNotFoundException("A product is required");
            }
            stockServ.subStockQuantity(productSale.getProduct().getId(), productSale.getQuantity());
        }
        sale.setProductSales(productSales);
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

    @Transactional
    public void deleteByID(int id) {
        Sale sale = saleRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No sale" +
                                                                                                " " +
                                                                                                "with " +
                                                                                                "id " + id + " to delete"));
        receiptRepo.deleteBySale_Id(id);
        saleRepo.deleteById(id);


    }
}
