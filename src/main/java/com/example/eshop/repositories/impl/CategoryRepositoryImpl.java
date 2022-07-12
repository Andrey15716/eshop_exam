package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Category;
import com.example.eshop.repositories.CategoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_CATEGORIES = "SELECT * FROM eshop2.categories";
    private static final String GET_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET name=? WHERE id=?";
    private static final String CREATE_NEW_CATEGORY = "INSERT INTO categories (id, name, image) VALUES (?, ?, ?)";
    private static final String DELETE_CATEGORY = "DELETE FROM categories WHERE id=?";

    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category create(Category entity) {
        jdbcTemplate.update(CREATE_NEW_CATEGORY, entity.getId(), entity.getName(), entity.getImageName());
        return getCategoryById(entity.getId());
    }

    @Override
    public List<Category> read() {
        return jdbcTemplate.query(GET_ALL_CATEGORIES, (rs, rowNum) -> Category.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image"))
                .build());
    }

    @Override
    public Category update(Category entity) {
        jdbcTemplate.update(UPDATE_CATEGORY, entity.getId(), entity.getName());
        return getCategoryById(entity.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_CATEGORY, id);
    }

    @Override
    public Category getCategoryById(int id) {
        return jdbcTemplate.queryForObject(GET_CATEGORY_BY_ID, (RowMapper<Category>) (rs, rowNum) -> Category.builder()
                .id(rs.getInt("id"))
                .name("name")
                .imageName("image")
                .build(), id);
    }
}