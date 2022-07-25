package com.example.eshop.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
@Getter
public class Cart extends BaseEntity {
    private Map<Integer, Product> products;
    private int totalPrice;
    private int quantity;

    public Cart() {
        this.products = new HashMap<>();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        totalPrice += product.getPrice();
    }

    public void clearCart() {
        products.clear();
    }

    public Cart(int totalPrice) {
        this.totalPrice = totalPrice * quantity;
    }
}