package com.example.eshop.repositories.impl;

import com.example.eshop.entities.User;
import com.example.eshop.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_USER_QUERY = "INSERT INTO eshop2.user(name,surname,password,date_of_birthday) VALUES(?,?,?,?)";
    private static final String INSERT_NEW_USER = "INSERT INTO eshop2.user(name,surname,password,date_of_birthday) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_USER_BY_LOG_AND_PASS = "SELECT * FROM eshop2.user WHERE name=? AND password=?";
    private static final String GET_ALL_USERS = "SELECT * FROM eshop2.user";
    private static final String UPDATE_USER = "UPDATE eshop2.user SET name=?,surname=?,password=?,date_of_birthday=? WHERE name=?";
    private static final String DELETE_USER = "DELETE FROM eshop2.user WHERE id=?";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User entity) {
        jdbcTemplate.update(INSERT_NEW_USER, entity.getName(), entity.getSurname(), entity.getPassword(), Date.valueOf(entity.getDateBorn()));
        return getUserByLoginAndPass(entity.getName(), entity.getPassword());
    }

    @Override
    public List<User> read() {
        return jdbcTemplate.query(GET_ALL_USERS, (rs, rowNum) -> User.builder()
                .id(rs.getInt("user_id"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .dateBorn(rs.getDate("date_of_birthday").toLocalDate())
                .build());
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update(UPDATE_USER, entity.getName(), entity.getSurname(),
                entity.getPassword(), Date.valueOf(entity.getDateBorn()), entity.getName());
        return getUserByLoginAndPass(entity.getName(), entity.getPassword());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public User getUserByLoginAndPass(String login, String password) {
        return jdbcTemplate.queryForObject(GET_USER_BY_LOG_AND_PASS, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("user_id"))
                .surname(rs.getString("surname"))
                .name(rs.getString("name"))
                .password(rs.getString("password"))
                .build(), login, password);
    }

    @Override
    public User addUser(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY, user.getName(), user.getSurname(), user.getPassword(), Date.valueOf(user.getDateBorn()));
        return user;
    }
}