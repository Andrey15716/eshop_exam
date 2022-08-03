package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.CategoryRepository;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SessionFactory sessionFactory;

    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SneakyThrows
    @Override
    public Category create(Category entity) throws RepositoryExceptions {
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
    public List<Category> read() throws RepositoryExceptions {
        List<Category> categories;
        try (Session session = sessionFactory.openSession()) {
            categories = session.createQuery("from  Category ").list();
        }
        return categories;
    }

    @Override
    public Category update(Category entity) throws RepositoryExceptions {
        Transaction transaction = null;
        Category category = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            category = session.get(Category.class, entity.getId());
            category.setName(entity.getName());
            category.setImageName(entity.getImageName());
            session.update(category);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Category category = session.get(Category.class, id);
            session.delete(category);
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
    public Category getCategoryById(int id) throws RepositoryExceptions {
        Transaction transaction;
        Category category = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            category = session.get(Category.class, id);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return category;
    }
}