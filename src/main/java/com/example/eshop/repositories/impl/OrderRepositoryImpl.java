package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Order;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final SessionFactory sessionFactory;

    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    private final JdbcTemplate jdbcTemplate;
//    private static final String INSERT_NEW_ORDER = "INSERT INTO eshop2.orders (price,date,user_id) VALUES (?,?,?)";
//    private static final String INSERT_NEW_ORDER_PRODUCT = "INSERT INTO eshop2.order_product (product_id,order_id) VALUES (?,?)";
//    private static final String GET_ALL_ORDERS_BY_USER_ID = "SELECT * FROM eshop2.orders WHERE user_id=?";
//    private static final String GET_ALL_ORDERS = "SELECT * FROM eshop2.orders";
//    private static final String UPDATE_ORDER = "UPDATE eshop2.orders SET price=? WHERE order_id=?";
//    private static final String DELETE_ORDER = "DELETE FROM eshop2.orders WHERE order_id=?";
//    private static final String GET_ORDER_BY_USER_ID = "SELECT * FROM eshop2.orders WHERE user_id=?";
//    private static final String GET_ORDER_BY_ORDER_ID = "SELECT * FROM eshop2.orders WHERE order_id=?";
//
//    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public Order create(Order entity) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
//        jdbcTemplate.update(INSERT_NEW_ORDER, entity.getPriceOrder(), Date.valueOf(entity.getDate()), entity.getUserId());
//        Order order = getOrderByUserId(entity.getUserId());
//        List<Product> productsInOrder = entity.getProductList();
//        for (Product product : productsInOrder) {
//            jdbcTemplate.update(INSERT_NEW_ORDER_PRODUCT, product.getId(), order.getId());
//            log.info("User with id " + entity.getUserId() + " created order with id " + order.getId());
//        }
//        return entity;
    }

    @Override
    public List<Order> read() throws RepositoryExceptions {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Order ").list();
//        return jdbcTemplate.query(GET_ALL_ORDERS, (rs, rowNum) -> Order.builder()
//                .id(rs.getInt("order_id"))
//                .priceOrder(rs.getInt("price"))
//                .date(rs.getDate("date").toLocalDate())
//                .userId(rs.getInt("user_id"))
//                .build());
    }

    @Override
    public Order update(Order entity) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, entity.getId());
        order.setPriceOrder(entity.getPriceOrder());
        order.setId(entity.getId());
        transaction.commit();
        session.close();
        return order;

//        jdbcTemplate.update(UPDATE_ORDER, entity.getPriceOrder(), entity.getId());
//        return getOrderById(entity.getId());
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, id);
        session.delete(order);
        transaction.commit();
        session.close();

//        jdbcTemplate.update(DELETE_ORDER, id);
    }

    @Override
    public List<Integer> getAllOrdersIdsByUserId(int id) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        return session.createQuery("from Order o where o.user.id=:id").list();
//        return jdbcTemplate.query(GET_ALL_ORDERS_BY_USER_ID, (rs, rowNum) ->
//                rs.getInt("order_id"), id);
    }

    @Override
    public Order getOrderByUserId(int id) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("select o from Order o where o.user.id=:id").list();
        Order order = session.get(Order.class, id);
        order.setId(order.getId());
        order.setPriceOrder(order.getPriceOrder());
        order.setDate(order.getDate());
        order.setId(id);
        transaction.commit();
        session.close();
        return order;

//        return jdbcTemplate.queryForObject(GET_ORDER_BY_USER_ID, (RowMapper<Order>) (rs, rowNum) -> Order.builder()
//                .id(rs.getInt("order_id"))
//                .priceOrder(rs.getInt("price"))
//                .date(rs.getDate("date").toLocalDate())
//                .userId(rs.getInt("user_id"))
//                .build(), id);
    }

    public Order getOrderById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("select o from Order o where o.id=:id").list();
        Order order = session.get(Order.class, id);
        order.setId(order.getId());
        order.setPriceOrder(order.getPriceOrder());
        order.setDate(order.getDate());
        transaction.commit();
        session.close();
        return order;
//        return jdbcTemplate.queryForObject(GET_ORDER_BY_ORDER_ID, (RowMapper<Order>) (rs, rowNum) -> Order.builder()
//                .id(rs.getInt("order_id"))
//                .priceOrder(rs.getInt("price"))
//                .date(rs.getDate("date").toLocalDate())
//                .userId(rs.getInt("user_id"))
//                .build(), id);
    }
}