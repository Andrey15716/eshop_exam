package com.example.eshop.repositories.impl;

import com.example.eshop.entities.User;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.eshop.utils.EshopConstants.LOGIN;
import static com.example.eshop.utils.EshopConstants.PASSWORD;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User create(User entity) throws RepositoryExceptions {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public List<User> read() throws RepositoryExceptions {
        List<User> users;
        try (Session session = sessionFactory.getCurrentSession()) {
            users = session.createQuery("from User").list();
        }
        return users;
    }

    @Override
    public User update(User entity) throws RepositoryExceptions {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
            user.setName(entity.getSurname());
            user.setSurname(entity.getSurname());
            user.setPassword(entity.getPassword());
            user.setDateBorn(entity.getDateBorn());
            session.update(user);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User getUserByLoginAndPass(User user) throws RepositoryExceptions {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("select u from User u where u.name=:login and u.password=:password");
        query.setParameter(LOGIN, user.getName());
        query.setParameter(PASSWORD, user.getPassword());
        return query.uniqueResult();
    }

    @Override
    public User addUser(User user) throws RepositoryExceptions {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return user;
    }
}