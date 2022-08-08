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
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Category create(Category entity) throws RepositoryExceptions {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Category> read() throws RepositoryExceptions {
        return entityManager.createQuery("select c from Category c").getResultList();
    }

    @Override
    public Category update(Category entity) throws RepositoryExceptions {
        Category category = entityManager.find(Category.class, entity.getId());
        category.setName(entity.getName());
        category.setImageName(entity.getImageName());
        entityManager.persist(category);
        return category;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
    }

    @Override
    public Category getCategoryById(int id) throws RepositoryExceptions {
        return entityManager.find(Category.class, id);
    }
}