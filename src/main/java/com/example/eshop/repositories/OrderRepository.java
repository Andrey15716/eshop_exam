package com.example.eshop.repositories;

import com.example.eshop.entities.Order;
import com.example.eshop.exceptions.RepositoryExceptions;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order getOrderById(int id) throws RepositoryExceptions;

    List<Integer> getAllOrdersIdsByUserId(int id) throws RepositoryExceptions;

    Order create(Order order) throws RepositoryExceptions;

    Order getOrderByUserId(int id) throws RepositoryExceptions;
}