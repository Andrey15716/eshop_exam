package com.example.eshop.repositories;

import com.example.eshop.entities.Order;
import com.example.eshop.exceptions.RepositoryExceptions;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    Order create(Order order) throws RepositoryExceptions;

    List<Order> getAllOrdersByUserId(int userId);

    List<Order> getAllOrdersByUserIdPagination(int userId, int number);

    long getNumberOfOrdersPerPage(int userId);
}