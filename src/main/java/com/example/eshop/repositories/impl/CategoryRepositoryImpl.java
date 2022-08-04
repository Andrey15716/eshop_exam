package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {
    //    private final SessionFactory sessionFactory;
    @PersistenceContext
    private EntityManager entityManager;

//    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public Category create(Category entity) throws RepositoryExceptions {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            session.save(entity);
//            transaction.commit();
//            session.close();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//        }
//        return entity;
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Category> read() throws RepositoryExceptions {
        return entityManager.createQuery("select c from Category c").getResultList();
//        List<Category> categories;
//        try (Session session = sessionFactory.openSession()) {
//            categories = session.createQuery("from  Category ").list();
//        }
//        return categories;
    }

    @Override
    public Category update(Category entity) throws RepositoryExceptions {
        Category category = entityManager.find(Category.class, entity.getId());
        category.setName(entity.getName());
        category.setImageName(entity.getImageName());
        entityManager.persist(category);
        return category;
//        Transaction transaction = null;
//        Category category = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            category = session.get(Category.class, entity.getId());
//            category.setName(entity.getName());
//            category.setImageName(entity.getImageName());
//            session.update(category);
//            transaction.commit();
//            session.close();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//        }
//        return category;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            Category category = session.get(Category.class, id);
//            session.delete(category);
//            transaction.commit();
//            session.close();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//        }
    }

    @Override
    public Category getCategoryById(int id) throws RepositoryExceptions {
        return entityManager.find(Category.class, id);
//        Transaction transaction;
//        Category category = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            category = session.get(Category.class, id);
//            transaction.commit();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return category;
    }
}