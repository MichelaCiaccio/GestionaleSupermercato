package com.example.supermarket.deals;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.ProductSale;

import java.math.BigDecimal;
import java.util.List;

public class DISCOUNTONTOTALDeal implements DealStrategy {
    @Override
    public BigDecimal applyDeal(List<ProductSale> productSales, Deal deal) {

        // Recuperare la categoria a cui fa riferimento la promozione
        List<Category> targetCategories = deal.getCategories();

        // Recupero i prodotti che hanno la stessa categoria di quelle in promozione
        List<Product> targetProducts =
                productSales.stream().map(ProductSale::getProduct).filter(product -> targetCategories.stream()
                        .anyMatch(category -> category.equals(product.getCategory()))).toList();

        BigDecimal totalPrice =
                targetProducts.stream().map(Product::getSellingPrice).reduce(BigDecimal.ZERO,
                                                                             BigDecimal::add);
        BigDecimal discountedPrice =
                targetProducts.stream().map(Product::getSellingPrice).reduce(BigDecimal.ZERO,
                                                                             BigDecimal::add).multiply(BigDecimal.valueOf(0.2));

        return totalPrice.compareTo(BigDecimal.valueOf(50)) >= 0 ?
                totalPrice.subtract(discountedPrice) : BigDecimal.ZERO;

    }
}
