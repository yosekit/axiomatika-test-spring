package com.yosekit.creditmanager.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddlAuto;

    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    private String currentSectionContextClass;

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    private Properties getProperties() {
        var properties = new Properties();

        properties.put(Environment.JAKARTA_JDBC_DRIVER, dbDriverClassName);
        properties.put(Environment.JAKARTA_JDBC_URL, dbUrl);
        properties.put(Environment.JAKARTA_JDBC_USER, dbUsername);
        properties.put(Environment.JAKARTA_JDBC_PASSWORD, dbPassword);
        properties.put(Environment.DIALECT, hibernateDialect);
        properties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        properties.put(Environment.SHOW_SQL, showSql);
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, currentSectionContextClass);

        return properties;
    }

    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(dbDriverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("com.yosekit.creditmanager.model");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        entityManager.setJpaProperties(this.getProperties());

        return entityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
