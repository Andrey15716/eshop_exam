package com.example.eshop.services;

import com.example.eshop.entities.Order;
import com.example.eshop.exceptions.ServiceExceptions;

public interface OrderService extends BaseServices<Order> {
    Order create(Order order) throws ServiceExceptions;
}