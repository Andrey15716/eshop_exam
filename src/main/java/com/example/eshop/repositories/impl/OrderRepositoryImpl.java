package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Order;
import com.example.eshop.entities.Product;
import com.example.eshop.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_NEW_ORDER = "INSERT INTO eshop2.orders (price,date,user_id) VALUES (?,?,?)";
    private static final String INSERT_NEW_ORDER_PRODUCT = "INSERT INTO eshop2.order_product (product_id,order_id) VALUES (?,?)";
    private static final String GET_ALL_ORDERS_BY_USER_ID = "SELECT * FROM eshop2.orders WHERE user_id=?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM eshop2.orders";
    private static final String UPDATE_ORDER = "UPDATE eshop2.orders SET price=? WHERE order_id=?";
    private static final String DELETE_ORDER = "DELETE FROM eshop2.orders WHERE order_id=?";
    private static final String GET_ORDER_BY_USER_ID = "SELECT * FROM eshop2.orders WHERE user_id=?";
    private static final String GET_ORDER_BY_ORDER_ID = "SELECT * FROM eshop2.orders WHERE order_id=?";

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order create(Order entity) {
        jdbcTemplate.update(INSERT_NEW_ORDER, entity.getPriceOrder(), Date.valueOf(entity.getDate()), entity.getUserId());
        Order order = getOrderByUserId(entity.getUserId());
        List<Product> productsInOrder = entity.getProductList();
        for (Product product : productsInOrder) {
            jdbcTemplate.update(INSERT_NEW_ORDER_PRODUCT, product.getId(), order.getId());
            log.info("User with id " + entity.getUserId() + " created order with id " + order.getId());
        }
        return entity;
    }

    @Override
    public List<Order> read() {
        return jdbcTemplate.query(GET_ALL_ORDERS, (rs, rowNum) -> Order.builder()
                .id(rs.getInt("order_id"))
                .priceOrder(rs.getInt("price"))
                .date(rs.getDate("date").toLocalDate())
                .userId(rs.getInt("user_id"))
                .build());
    }

    @Override
    public Order update(Order entity) {
        jdbcTemplate.update(UPDATE_ORDER, entity.getPriceOrder(), entity.getId());
        return getOrderById(entity.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_ORDER, id);
    }

    @Override
    public List<Integer> getAllOrdersIdsByUserId(int id) {
        return jdbcTemplate.query(GET_ALL_ORDERS_BY_USER_ID, (rs, rowNum) ->
                rs.getInt("order_id"), id);
    }

    @Override
    public Order getOrderByUserId(int id) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_USER_ID, (RowMapper<Order>) (rs, rowNum) -> Order.builder()
                .id(rs.getInt("order_id"))
                .priceOrder(rs.getInt("price"))
                .date(rs.getDate("date").toLocalDate())
                .userId(rs.getInt("user_id"))
                .build(), id);
    }

    public Order getOrderById(int id) {
        return jdbcTemplate.queryForObject(GET_ORDER_BY_ORDER_ID, (RowMapper<Order>) (rs, rowNum) -> Order.builder()
                .id(rs.getInt("order_id"))
                .priceOrder(rs.getInt("price"))
                .date(rs.getDate("date").toLocalDate())
                .userId(rs.getInt("user_id"))
                .build(), id);
    }
}