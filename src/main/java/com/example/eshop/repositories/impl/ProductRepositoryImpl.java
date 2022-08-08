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

    @Override
    public Product create(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Product> read() throws RepositoryExceptions {
        return entityManager.createQuery("select p from Product p").getResultList();
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
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
    }

    @Override
    public List<Product> getProductsBySearchRequest(String param) throws RepositoryExceptions {
        Query query = entityManager.createQuery("select p from Product p where p.name like :searchResult or p.description like: searchResult");
        String searchResult = '%' + param + '%';
        query.setParameter(SEARCH_RESULT, searchResult);
        return query.getResultList();
    }

    @Override
    public Product getProductById(int id) throws RepositoryExceptions {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) throws RepositoryExceptions {
        Query query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId");
        query.setParameter(CATEGORY_ID, categoryId);
        return query.getResultList();
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
    }
}