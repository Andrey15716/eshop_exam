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
//    private final JdbcTemplate jdbcTemplate;
//    private static final String GET_ALL_CATEGORIES = "SELECT * FROM eshop2.categories";
//    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
//    private static final String UPDATE_CATEGORY = "UPDATE categories SET name=? WHERE id=?";
//    private static final String CREATE_NEW_CATEGORY = "INSERT INTO categories (id, name, image) VALUES (?, ?, ?)";
//    private static final String DELETE_CATEGORY = "DELETE FROM categories WHERE id=?";
//
//    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @SneakyThrows
    @Override
    public Category create(Category entity) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public List<Category> read() throws RepositoryExceptions {
//        return jdbcTemplate.query(GET_ALL_CATEGORIES, (rs, rowNum) -> Category.builder()
//                .id(rs.getInt("id"))
//                .name(rs.getString("name"))
//                .imageName(rs.getString("image"))
//                .build());
        Session session = sessionFactory.openSession();
        return session.createQuery("from Category").list();
    }

    @Override
    public Category update(Category entity) throws RepositoryExceptions {
//        jdbcTemplate.update(UPDATE_CATEGORY, entity.getId(), entity.getName());
//        return getCategoryById(entity.getId());
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Category category = session.get(Category.class, entity.getId());
        category.setName(entity.getName());
        category.setImageName(entity.getImageName());
        session.update(category);
        transaction.commit();
        session.close();
        return category;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
//        jdbcTemplate.update(DELETE_CATEGORY, id);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Category category = session.get(Category.class, id);
        session.delete(category);
        transaction.commit();
        session.close();
    }

    @Override
    public Category getCategoryById(int id) throws RepositoryExceptions {
//        return jdbcTemplate.queryForObject(GET_CATEGORY_BY_ID, (RowMapper<Category>) (rs, rowNum) -> Category.builder()
//                .id(rs.getInt("id"))
//                .name("name")
//                .imageName("image")
//                .build(), id);
        Session session = sessionFactory.openSession();
        return session.get(Category.class, id);
    }
}