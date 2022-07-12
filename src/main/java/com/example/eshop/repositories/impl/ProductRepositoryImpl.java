package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Product;
import com.example.eshop.repositories.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM eshop2.products WHERE category_id=?";
    private static final String GET_ALL_PRODUCTS_BY_ORDER_ID = "SELECT eshop2.products.*\n" +
            "FROM eshop2.products\n" +
            "INNER JOIN eshop2.order_product\n" +
            "ON order_product.product_id = eshop2.products.id\n" +
            "INNER JOIN eshop2.orders\n" +
            "ON eshop2.orders.order_id = eshop2.order_product.order_id\n" +
            "WHERE eshop2.order_product.order_id = ?";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM eshop2.products WHERE id=?";
    private static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String GET_PRODUCTS_BY_SEARCH_REQUEST = " SELECT * FROM eshop2.products WHERE name LIKE ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id=?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name=?, description=?, price=?, category_id=?, image_name=? WHERE id=?";
    private static final String INSERT_NEW_PRODUCT = "INSERT INTO products (name, description, price, category_id, image_name) VALUES (?, ?, ?, ?, ?)";


    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product create(Product entity) {
        jdbcTemplate.update(INSERT_NEW_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(), entity.getImageName());
        return entity;
    }

    @Override
    public List<Product> read() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS, (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .imageName(rs.getString("image_name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .categoryId(rs.getInt("category_id"))
                .build()
        );
    }

    @Override
    public Product update(Product entity) {
        jdbcTemplate.update(UPDATE_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(), entity.getImageName(), entity.getId());
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }

    @Override
    public List<Product> getAllProductsByOrderId(int id) {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_BY_ORDER_ID, (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .categoryId(rs.getInt("category_id"))
                .imageName(rs.getString("image_name"))
                .build(), id);
    }

    @Override
    public List<Product> getProductsBySearchRequest(String param) {
        String s = '%' + param + '%';
        return jdbcTemplate.query(GET_PRODUCTS_BY_SEARCH_REQUEST, (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .categoryId(rs.getInt("category_id"))
                .imageName(rs.getString("image_name"))
                .build(), s);
    }

    @Override
    public Product getProductById(int id) {
        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID, (RowMapper<Product>) (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .categoryId(rs.getInt("category_id"))
                .imageName(rs.getString("image_name"))
                .build(), id);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_BY_CATEGORY_ID, (rs, rowNum) -> Product.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .categoryId(rs.getInt("category_id"))
                .imageName(rs.getString("image_name"))
                .build(), categoryId);
    }
}