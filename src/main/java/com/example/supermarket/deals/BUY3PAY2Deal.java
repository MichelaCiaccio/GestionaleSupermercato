package com.example.supermarket.deals;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.ProductSale;
import com.example.supermarket.entity.enums.DealType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class BUY3PAY2Deal implements DealStrategy {
    
    @Override
    public BigDecimal applyDeal(List<ProductSale> productSales, Deal deal) {
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
