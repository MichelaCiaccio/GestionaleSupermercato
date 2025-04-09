package com.example.supermarket.deals;

import com.example.supermarket.entity.Category;
import com.example.supermarket.entity.Deal;
import com.example.supermarket.entity.Product;
import com.example.supermarket.entity.ProductSale;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class BUY3PAY2Deal implements DealStrategy {

    /**
     * This method applies the deal.
     * It identifies the products belonging to the same category as the deal's target
     * category,
     * calculates the number of groups of 3 products,
     * selects the least expensive products from
     * those groups,
     * and returns the total price of the free products.
     *
     * @param productSales The list of product sales to apply the deal to.
     * @param deal         The deal to apply.
     * @return The total price of the free products based on the deal.
     */
    @Override
    public BigDecimal applyDeal(List<ProductSale> productSales, Deal deal) {

        System.out.println(deal.toString());
        // Recuperare la categoria a cui fa riferimento la promozione
        List<Category> targetCategories = deal.getCategories();

        // Recupero i prodotti che hanno la stessa categoria di quelle in promozione
        List<Product> targetProducts =
                productSales.stream().map(ProductSale::getProduct).filter(product -> targetCategories.stream()
                        .anyMatch(category -> category.equals(product.getCategory()))).toList();

        // Conto quanti gruppi da 3 prodotti esistono
        int groupProducts = targetProducts.size() / 3;

        List<Product> freeProducts =
                targetProducts.stream().sorted(Comparator.comparing(Product::getSellingPrice)).limit(groupProducts).toList();

        // Restituisco i prodotti meno cari in basi al numero di gruppi da 3
        return freeProducts.stream().map(Product::getSellingPrice).reduce(BigDecimal.ZERO,
                                                                          BigDecimal::add);
    }
}
