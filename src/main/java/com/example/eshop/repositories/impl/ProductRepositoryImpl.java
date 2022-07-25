package com.example.eshop.repositories.impl;

import com.example.eshop.entities.Product;
import com.example.eshop.exceptions.RepositoryExceptions;
import com.example.eshop.repositories.ProductRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final SessionFactory sessionFactory;

    public ProductRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    private final JdbcTemplate jdbcTemplate;
//    private static final String GET_ALL_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM eshop2.products WHERE category_id=?";
//    private static final String GET_ALL_PRODUCTS_BY_ORDER_ID = "SELECT eshop2.products.*\n" +
//            "FROM eshop2.products\n" +
//            "INNER JOIN eshop2.order_product\n" +
//            "ON order_product.product_id = eshop2.products.id\n" +
//            "INNER JOIN eshop2.orders\n" +
//            "ON eshop2.orders.order_id = eshop2.order_product.order_id\n" +
//            "WHERE eshop2.order_product.order_id = ?";
//    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM eshop2.products WHERE id=?";
//    private static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
//    private static final String GET_PRODUCTS_BY_SEARCH_REQUEST = " SELECT * FROM eshop2.products WHERE name LIKE ?";
//    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id=?";
//    private static final String UPDATE_PRODUCT = "UPDATE products SET name=?, description=?, price=?, category_id=?, image_name=? WHERE id=?";
//    private static final String INSERT_NEW_PRODUCT = "INSERT INTO products (name, description, price, category_id, image_name) VALUES (?, ?, ?, ?, ?)";
//
//
//    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public Product create(Product entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
//        jdbcTemplate.update(INSERT_NEW_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(), entity.getImageName());
//        return entity;
    }

    @Override
    public List<Product> read() throws RepositoryExceptions {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Product ").list();
//        return jdbcTemplate.query(GET_ALL_PRODUCTS, (rs, rowNum) -> Product.builder()
//                .id(rs.getInt("id"))
//                .name(rs.getString("name"))
//                .imageName(rs.getString("image_name"))
//                .description(rs.getString("description"))
//                .price(rs.getInt("price"))
//                .categoryId(rs.getInt("category_id"))
//                .build()
//        );
    }

    @Override
    public Product update(Product entity) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, entity.getId());
        product.setName(product.getImageName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setCategory(entity.getCategory());
        product.setImageName(entity.getImageName());
        product.setId(entity.getId());
        session.update(product);
        transaction.commit();
        session.close();
        return product;
//        jdbcTemplate.update(UPDATE_PRODUCT, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategoryId(), entity.getImageName(), entity.getId());
//        return entity;
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.delete(product);
        transaction.commit();
        session.close();
//        jdbcTemplate.update(DELETE_PRODUCT, id);
    }

    @Override
    public List<Product> getAllProductsByOrderId(int id) throws RepositoryExceptions {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:id");
        query.setParameter("id", id);
        return query.list();
//        return jdbcTemplate.query(GET_ALL_PRODUCTS_BY_ORDER_ID, (rs, rowNum) -> Product.builder()
//                .id(rs.getInt("id"))
//                .name(rs.getString("name"))
//                .description(rs.getString("description"))
//                .price(rs.getInt("price"))
//                .categoryId(rs.getInt("category_id"))
//                .imageName(rs.getString("image_name"))
//                .build(), id);
    }

    @Override
    public List<Product> getProductsBySearchRequest(String param) throws RepositoryExceptions {
        String searchResult = '%' + param + '%';
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p where p.name like :searchResult or p.description like: searchResult");
        query.setParameter("searchResult", searchResult);
        return query.list();
//        String s = '%' + param + '%';
//        return jdbcTemplate.query(GET_PRODUCTS_BY_SEARCH_REQUEST, (rs, rowNum) -> Product.builder()
//                .id(rs.getInt("id"))
//                .name(rs.getString("name"))
//                .description(rs.getString("description"))
//                .price(rs.getInt("price"))
//                .categoryId(rs.getInt("category_id"))
//                .imageName(rs.getString("image_name"))
//                .build(), s);
    }

    @Override
    public Product getProductById(int id) throws RepositoryExceptions {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
//        return jdbcTemplate.queryForObject(GET_PRODUCT_BY_ID, (RowMapper<Product>) (rs, rowNum) -> Product.builder()
//                .id(rs.getInt("id"))
//                .name(rs.getString("name"))
//                .description(rs.getString("description"))
//                .price(rs.getInt("price"))
//                .categoryId(rs.getInt("category_id"))
//                .imageName(rs.getString("image_name"))
//                .build(), id);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) throws RepositoryExceptions {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:categoryId");
        query.setParameter("categoryId", categoryId);
        return query.list();
//        return jdbcTemplate.query(GET_ALL_PRODUCTS_BY_CATEGORY_ID, (rs, rowNum) -> Product.builder()
//                .id(rs.getInt("id"))
//                .name(rs.getString("name"))
//                .description(rs.getString("description"))
//                .price(rs.getInt("price"))
//                .categoryId(rs.getInt("category_id"))
//                .imageName(rs.getString("image_name"))
//                .build(), categoryId);
    }
}