package com.example.eshop.repositories.impl;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static com.example.eshop.utils.EshopConstants.LOGIN;
import static com.example.eshop.utils.EshopConstants.PASSWORD;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(User entity) throws RepositoryExceptions {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<User> read() throws RepositoryExceptions {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User update(User entity) throws RepositoryExceptions {
        User user = entityManager.find(User.class, entity.getId());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setPassword(entity.getPassword());
        user.setDateBorn(entity.getDateBorn());
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUserByLoginAndPass(User user) throws RepositoryExceptions {
        Query query = entityManager.createQuery("select u from User u where u.name=:login and u.password=:password");
        query.setParameter(LOGIN, user.getName());
        query.setParameter(PASSWORD, user.getPassword());
        return (User) query.getSingleResult();
    }

    @Override
    public User addUser(User user) throws RepositoryExceptions {
        entityManager.persist(user);
        return user;
    }
}