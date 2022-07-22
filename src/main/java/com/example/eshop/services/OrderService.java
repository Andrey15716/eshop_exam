package com.example.eshop.services;

import com.example.eshop.entities.Order;

public interface OrderService extends BaseServices<Order> {
    Order create(Order order);
}