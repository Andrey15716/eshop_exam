package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static com.example.eshop.utils.EshopConstants.CATEGORY_ID;
import static com.example.eshop.utils.EshopConstants.PAGE_SIZE;
import static com.example.eshop.utils.EshopConstants.SEARCH_RESULT;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;
//    private final SessionFactory sessionFactory;

//    public ProductRepositoryImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public Product create(Product entity) {
        entityManager.persist(entity);
        return entity;
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
    }

    @Override
    public List<Product> read() throws RepositoryExceptions {
        return entityManager.createQuery("select p from Product p").getResultList();
//        List<Product> productList;
//        try (Session session = sessionFactory.getCurrentSession()) {
//            productList = session.createQuery("from Product ").list();
//        }
//        return productList;
    }

    @Override
    public Product update(Product entity) throws RepositoryExceptions {
        Product product = entityManager.find(Product.class, entity.getId());
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());
        product.setCategory(product.getCategory());
        product.setImageName(product.getImageName());
        product.setId(product.getId());
        entityManager.persist(product);
        return product;
//        Transaction transaction = null;
//        Product product = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            product = session.get(Product.class, entity.getId());
//            product.setName(product.getImageName());
//            product.setDescription(entity.getDescription());
//            product.setPrice(entity.getPrice());
//            product.setCategory(entity.getCategory());
//            product.setImageName(entity.getImageName());
//            product.setId(entity.getId());
//            session.update(product);
//            transaction.commit();
//            session.close();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            System.out.println(e.getMessage());
//        }
//        return product;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            Product product = session.get(Product.class, id);
//            session.delete(product);
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
    public List<Product> getProductsBySearchRequest(String param) throws RepositoryExceptions {
        Query query = entityManager.createQuery("select p from Product p where p.name like :searchResult or p.description like: searchResult");
        String searchResult = '%' + param + '%';
        query.setParameter(SEARCH_RESULT, searchResult);
        return query.getResultList();
//        String searchResult = '%' + param + '%';
//        Session session = sessionFactory.getCurrentSession();
//        Query<Product> query = session.createQuery("select p from Product p where p.name like :searchResult or p.description like: searchResult");
//        query.setParameter(SEARCH_RESULT, searchResult);
//        return query.list();
    }

    @Override
    public Product getProductById(int id) throws RepositoryExceptions {
        return entityManager.find(Product.class, id);
//        Product product;
//        try (Session session = sessionFactory.getCurrentSession()) {
//            product = session.get(Product.class, id);
//        }
//        return product;
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) throws RepositoryExceptions {
        Query query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId");
        query.setParameter(CATEGORY_ID, categoryId);
        return query.getResultList();
//        Session session = sessionFactory.getCurrentSession();
//        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:categoryId");
//        query.setParameter(CATEGORY_ID, categoryId);
//        return query.list();
    }

    @Override
    public List<Product> getAllProductsByCategoryIdPaging(int categoryId, int pageReq) {
        int firstResult;
        if (pageReq > 1) {
            firstResult = (pageReq - 1) * PAGE_SIZE;
        } else {
            firstResult = 0;
        }
        Query query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId order by p.name asc");
        query.setParameter(CATEGORY_ID, categoryId);
        query.setFirstResult(firstResult);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public long getNumberOfProductsPerPage(int categoryId) {
        Query query = entityManager.createQuery("select count(p) from Product p where p.category.id=:categoryId");
        query.setParameter(CATEGORY_ID, categoryId);
        long resultQuery = (long) query.getSingleResult();
        if (resultQuery % PAGE_SIZE != 0) {
            return resultQuery / PAGE_SIZE + 1;
        }
        return resultQuery / PAGE_SIZE;
//        Session session = sessionFactory.getCurrentSession();
//        Query<Long> query = session.createQuery("select count(p) from Product p where p.category.id=:categoryId");
//        query.setParameter(CATEGORY_ID, categoryId);
//        long resultQuery = query.getSingleResult();
//        if (resultQuery % PAGE_SIZE != 0) {
//            return query.getSingleResult() / PAGE_SIZE + 1;
//        }
//        return query.getSingleResult() / PAGE_SIZE;
    }
}