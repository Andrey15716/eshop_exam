package com.example.eshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dm.setUrl("jdbc:mysql://localhost:3306/eshop2");
        dm.setUsername("root");
        dm.setPassword("qwe345qwe");
        return dm;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}