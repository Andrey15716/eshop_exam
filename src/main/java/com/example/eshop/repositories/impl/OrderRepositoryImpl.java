package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Order;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.OrderRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static com.example.eshop.utils.EshopConstants.ID;
import static com.example.eshop.utils.EshopConstants.ORDER_PAGE_SIZE;
import static com.example.eshop.utils.EshopConstants.PAGE_SIZE;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order entity) throws RepositoryExceptions {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Order> read() throws RepositoryExceptions {
        return entityManager.createQuery("select o from Order o").getResultList();
    }

    @Override
    public Order update(Order entity) throws RepositoryExceptions {
        Order order = entityManager.find(Order.class, entity.getId());
        order.setPriceOrder(entity.getPriceOrder());
        order.setId(entity.getId());
        entityManager.persist(order);
        return order;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
    }

    @Override
    public List<Order> getAllOrdersByUserId(int userId) {
        Query query = entityManager.createQuery("select o from Order o where o.user.id=:id order by o.id desc");
        query.setParameter(ID, userId);
        return query.getResultList();
    }

    @Override
    public List<Order> getAllOrdersByUserIdPagination(int userId, int number) {
        Query query = entityManager.createQuery("select o from Order o where o.user.id=:id order by o.id desc");
        int firstResult;
        if (number > 1) {
            firstResult = (number - 1) * PAGE_SIZE;
        } else {
            firstResult = 0;
        }
        query.setParameter(ID, userId);
        query.setFirstResult(firstResult);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public long getNumberOfOrdersPerPage(int userId) {
        Query query = entityManager.createQuery("select count(o) from Order o where o.user.id=:id");
        query.setParameter(ID, userId);
        long resultQuery = (long) query.getSingleResult();
        if (resultQuery % ORDER_PAGE_SIZE != 0) {
            return resultQuery / ORDER_PAGE_SIZE + 1;
        }
        return resultQuery / ORDER_PAGE_SIZE;
    }
}