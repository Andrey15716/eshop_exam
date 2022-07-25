package com.example.eshop.repositories;

import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getAllProductsByCategoryId(int categoryId) throws RepositoryExceptions;

    List<Product> getAllProductsByOrderId(int id) throws RepositoryExceptions;

    List<Product> getProductsBySearchRequest(String param) throws RepositoryExceptions;

    Product getProductById(int id) throws RepositoryExceptions;
}