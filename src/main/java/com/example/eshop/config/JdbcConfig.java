package com.example.eshop.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

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

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
//        Properties properties = new Properties();
        // See: application.properties
        // properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        // properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        // properties.put("current_session_context_class",
        // env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        // Fix Postgres JPA Error: // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
        // properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean(); // Package contain entity classes factoryBean.setPackagesToScan(new String[] { "" });
        factoryBean.setDataSource(dataSource);
//         factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet(); //
        SessionFactory sf = factoryBean.getObject();
        System.out.println("## getSessionFactory: " + sf);
        return sf;
    }
}