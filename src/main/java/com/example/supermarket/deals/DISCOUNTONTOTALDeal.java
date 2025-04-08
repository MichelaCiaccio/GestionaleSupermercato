package com.example.supermarket.deals;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.ProductSale;

import java.math.BigDecimal;
import java.util.List;

public class DISCOUNTONTOTALDeal implements DealStrategy {

    /**
     * This method applies a deal to a list of product sales and calculates the savings.
     * It identifies the products from the provided sales that belong to the categories
     * targeted by the deal.
     * If the total price of these products exceeds or equals 50, a 20% discount
     * is applied, and the resulting savings are returned.
     *
     * @param productSales The list of product sales
     * @param deal         The promotional deal to apply
     * @return The amount saved by applying the deal
     */
    @Override
    public BigDecimal applyDeal(List<ProductSale> productSales, Deal deal) {

        // Recuperare la categoria a cui fa riferimento la promozione
        List<Category> targetCategories = deal.getCategories();

        // Recupero i prodotti che hanno la stessa categoria di quelle in promozione
        List<Product> targetProducts =
                productSales.stream().map(ProductSale::getProduct).filter(product -> targetCategories.stream()
                        .anyMatch(category -> category.equals(product.getCategory()))).toList();

        // Calcolo il prezzo totale dei prodotti target
        BigDecimal totalPrice =
                targetProducts.stream().map(Product::getSellingPrice).reduce(BigDecimal.ZERO,
                                                                             BigDecimal::add);
        // Calcolo il prezzo totale applicando lo sconto della promozione
        BigDecimal discountedPrice =
                targetProducts.stream().map(Product::getSellingPrice).reduce(BigDecimal.ZERO,
                                                                             BigDecimal::add).multiply(BigDecimal.valueOf(0.2));

        // Ritorno il risparmio dato dall'applicazione della promozione
        return totalPrice.compareTo(BigDecimal.valueOf(50)) >= 0 ?
                totalPrice.subtract(discountedPrice) : BigDecimal.ZERO;

    }
}
