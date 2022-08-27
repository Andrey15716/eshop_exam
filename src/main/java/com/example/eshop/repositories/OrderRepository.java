package com.example.eshop.repositories;

import com.example.eshop.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> getOrdersByUserId(int userId, Pageable pageable);
}