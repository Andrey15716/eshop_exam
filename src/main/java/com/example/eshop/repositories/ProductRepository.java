package com.example.eshop.repositories;

import com.example.eshop.entities.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getAllProductsByCategoryId(int categoryId);

    List<Product> getAllProductsByOrderId(int id);

    List<Product> getProductsBySearchRequest(String param);

    Product getProductById(int id);
}