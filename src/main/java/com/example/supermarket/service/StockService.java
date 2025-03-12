package com.example.supermarket.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.supermarket.entity.Stock;
import com.example.supermarket.repo.StockRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    /**
     * Metodo per ricercare tutti gli stock presente nel database e restituirli. In
     * caso di assenza di Stock lancia una eccezzione e con un messaggio di errore.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @return
     */
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll(PageRequest.of(0, 20)).getContent();
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks");
        }
        return stocks;
    }

    /**
     * Metodo per ricercare lo stock di prodotti tramite il nome del prodotto
     * immagazzinato. In
     * caso di assenza viene lanciata una eccezzione con un messaggio di errore.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param name
     * @return
     */
    public List<Stock> findByProductName(String name) {
        List<Stock> stocks = stockRepository.findByProductName(name, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no product with name " + name + " in stock");
        }
        return stocks;
    }

    /**
     * Metodo per cercare gli stock di prodotti tramite il nome del fornitore.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param name
     * @return
     */
    public List<Stock> findBySupplierName(String name) {
        List<Stock> stocks = stockRepository.findBySupplierName(name, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no stocks from supplier with name " + name);
        }
        return stocks;
    }

    /**
     * Metodo per cercare gli stock di prodotti con giacenza maggiore di una
     * certa quantità.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param quantity
     * @return
     */
    public List<Stock> findByQuantityGreaterThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityGreaterThan(quantity, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity greater than " + quantity);
        }
        return stocks;
    }

    /**
     * Metodo per cercare gli stock di prodotti con una giacenza minore di una
     * certa quantità.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param quantity
     * @return
     */
    public List<Stock> findByQuantityLessThan(int quantity) {
        List<Stock> stocks = stockRepository.findByQuantityLessThan(quantity, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products with a stock quantity less than " + quantity);
        }
        return stocks;
    }

    /**
     * Metodo per cercare gli stock di prodotti con una data di consegna uguale a
     * quella fornita.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param deliveryDate
     * @return
     */
    public List<Stock> findByDeliveryDate(LocalDate deliveryDate) {
        List<Stock> stocks = stockRepository.findByDeliveryDate(deliveryDate, PageRequest.of(0, 20));
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException("There are no products in stock arrived on " + deliveryDate);
        }
        return stocks;
    }

    /**
     * Metodo per cercare gli stock di prodotti con data da scadenza uguale a quella
     * fornita.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param expirationDate
     * @return
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
     * Metodo per cercare stock di prodotti con un data di scadenza compresa tra due
     * date fornite.
     * Gli elementi vengono restituiti con una paginazione di 20 stock per pagina
     * 
     * @param firstDate
     * @param seconDate
     * @return
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
     * Questo metodo recupera lo stock relativo ad un prodotto specifico,
     * recuperandolo tramite id, e ne aggiorna la quantità sommando quella già
     * esistente a quella nuova
     * 
     * @param newStockQuantity
     * @param productId
     */
    public ResponseEntity<String> updateQuantity(int newStockQuantity, int productId) {
        Stock stock = stockRepository.findByProductId(productId);
        stock.setQuantity(stock.getQuantity() + newStockQuantity);
        return ResponseEntity.ok("Quantity for product " + stock.getProduct().getName() + " updated successfully");

    }

    /**
     * Metodo per creare un nuovo stock di un determinato prodotto. Prima di
     * salvarlo cerca se questo non sia già esistente in database. Se esiste
     * già restituisce un errrore con un messaggio che rimanada ad un altro metodo.
     * In caso di riscontro negativo invece prodede a salvare nel database il nuovo
     * stock
     * 
     * @param stock
     */
    public void save(Stock stock) {
        if (stockRepository.existsByProductNameAndSupplierName(stock.getProduct().getName(),
                stock.getSupplier().getName())) {
            throw new DuplicateKeyException(
                    "The stock for this product already exists. Instead of creating new stock, update the quantity");
        }
        stockRepository.save(stock);
    }

}
