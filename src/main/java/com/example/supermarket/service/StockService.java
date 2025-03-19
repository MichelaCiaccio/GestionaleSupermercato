package com.example.supermarket.service;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class
StockService {

    @Autowired
    private StockRepository stockRepository;

    /**
     * The method calls the stockRepository findAll to search for all the stocks
     * if there are no stocks throws an EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @return the stocks found
     */
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll(PageRequest.of(0, 20)).getContent();
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks");
        }
        return stocks;
    }

    /**
     * This method searches for stocks using the name as a parameter
     * if the stocks are empty throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param name the name of the stock
     * @return the stocks found
     */
    public List<Stock> findByProductName(String name) {
        List<Stock> stocks = stockRepository.findByProductName(name, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no product with name " + name + " in stock");
        }
        return stocks;
    }

    /**
     * This method searches for stocks using the name of the supplier as a parameter
     * if the stocks are empty throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param name the name of the supplier
     * @return the stocks found
     */
    public List<Stock> findBySupplierName(String name) {
        List<Stock> stocks = stockRepository.findBySupplierName(name, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks from supplier with name " + name);
        }
        return stocks;
    }

    /**
     * This method searches for amounts of stocks greater than the given quantity
     * if there are no stocks with a quantity greater than the one indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param quantity
     * @return the stocks found
     */
    public List<Stock> findByQuantityGreaterThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityGreaterThan(quantity, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity greater than " + quantity);
        }
        return stocks;
    }

    /**
     * This method searches for amounts of stocks lower than the given quantity
     * if there are no stocks with a quantity lower than the one indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param quantity
     * @return the stocks found
     */
    public List<Stock> findByQuantityLessThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityLessThan(quantity, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity less than " + quantity);
        }
        return stocks;
    }

    /**
     * This method searches for stocks with a deliveryDate corresponding to the one
     * given as the input parameter
     * if there are no stocks corresponding to the filter indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param deliveryDate
     * @return the stocks found
     */
    public List<Stock> findByDeliveryDate(LocalDate deliveryDate) {
        List<Stock> stocks = stockRepository.findByDeliveryDate(deliveryDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products in stock arrived on " + deliveryDate);
        }
        return stocks;
    }

    /**
     * This method searches for stocks with an expirationDate corresponding to the
     * one
     * given as the input parameter
     * if there are no stocks corresponding to the filter indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param expirationDate
     * @return the stocks found
     */
    public List<Stock> findByExpirationDate(LocalDate expirationDate) {
        List<Stock> stocks = stockRepository.findByExpirationDate(expirationDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(
                    "There are no products in stock with expiration date equals to " + expirationDate);
        }
        return stocks;
    }

    /**
     * This method searches for stocks with an expirationDate between the ones
     * provided as the input parameter
     * if there are no stocks corresponding to the filter indicated
     * the method throws a EntityNotFoundException.
     * The elements found are returned with 20 items for page pagination
     *
     * @param firstDate
     * @param seconDate
     * @return the stocks found
     */
    public List<Stock> findByExpirationDateBetween(LocalDate firstDate, LocalDate seconDate) {
        List<Stock> stocks = stockRepository.findByExpirationDateBetween(firstDate, seconDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(
                    "There are no products in stock with exipration date betweem " + firstDate + " and " + seconDate);
        }
        return stocks;
    }

    /**
     * This method searches and updates the stock of a specific product
     * the method calls the stockRepository.findById to search for the specific
     * stock
     * then modifies it by adding the new quantity indicated as an input
     *
     * @param newStockQuantity the quantity to add to the stock
     * @param productId        the id of the product
     */
    public ResponseEntity<String> updateQuantity(int newStockQuantity, int productId) {
        Stock stock = stockRepository.findByProductId(productId);
        stock.setQuantity(stock.getQuantity() + newStockQuantity);
        return ResponseEntity.ok("Quantity for product " + stock.getProduct().getName() + " updated successfully");

    }

    /**
     * This method creates a new stock of a product.
     * First, it checks if the supplier already exists and if it doesn't, throws a
     * IllegalArgumentException. If the supplier exists, it proceeds to the next
     * check.
     * Next, it checks if the stock already exists and if it does, throws a
     * DuplicateKeyException,
     * otherwise calls the stockRepository.save method to create the new stock.
     *
     * @param stock
     */
    public void save(Stock stock) {
        if (stock.getSupplier() == null) {
            throw new DataIntegrityViolationException("Stock must have a supplier");
        }
        if (stockRepository.existsByProductNameAndSupplierNameAndExpirationDate(stock.getProduct().getName(),
                stock.getSupplier().getName(), stock.getExpirationDate())) {
            throw new DuplicateKeyException(
                    "The stock for " + stock.getProduct().getName()
                            + " from " + stock.getSupplier().getName() + " with expiration"
                            + " already exists. Instead of creating new stock, update the quantity");
        }
        stockRepository.save(stock);
    }

    /**
     * This method updates a stock identified by its ID.
     * Checks if the stock exists, and if it doesn't, throws an
     * EntityNotFoundException.
     * Otherwise, it proceeds to update the stock's attribute with the new
     * information
     * and saves the modified stock.
     *
     * @param id       The ID of the stock to be updated.
     * @param modStock The new stock data to update with.
     */

    public void update(int id, Stock modStock) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No stock with id " + id));

        stock.setQuantity(modStock.getQuantity());
        stock.setDeliveryDate(modStock.getDeliveryDate());
        stock.setExpirationDate(modStock.getExpirationDate());
        stock.setProduct(modStock.getProduct());
        stock.setSupplier(modStock.getSupplier());
        stockRepository.save(stock);
    }

    /**
     * This method deletes a stock identified by its id.
     * Check if the stock exists and, if it does, proceed to call
     * stockRepository.delete to delete it.
     * Otherwise it throws an EntityNotFoundException
     *
     * @param id
     */
    public void deleteByID(int id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No stock with this id to delete"));
        stockRepository.delete(stock);
    }

    /**
     * This method delete all the stocks.
     * Checks if the stocks exist, and if they don't, throws an
     * EntityNotFoundException.
     * Otherwise, it proceeds to call stockRepository.deleteAll to delete them all.
     */

    public void deleteALl() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks to delete");
        }
        stockRepository.deleteAll();
    }

}
