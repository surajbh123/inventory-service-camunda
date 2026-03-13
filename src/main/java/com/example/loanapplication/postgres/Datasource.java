package com.example.loanapplication.postgres;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.loanapplication.postgres",
        entityManagerFactoryRef = Datasource.POSTGRES_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = Datasource.POSTGRES_TRANSACTION_MANAGER
)
public class Datasource {

    public static final String POSTGRES_DATASOURCE = "postgres";
    public static final String POSTGRES_ENTITY_MANAGER_FACTORY = "postgresEntityManagerFactory";
    public static final String POSTGRES_TRANSACTION_MANAGER = "postgresTransactionManager";
    public static final String POSTGRES_ENTITY_MANAGER = "postgresEntityManager";

    @Bean
    @Qualifier(POSTGRES_DATASOURCE)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/camunda")
                .username("camunda")
                .password("camunda")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier(POSTGRES_DATASOURCE) DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = POSTGRES_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier(POSTGRES_DATASOURCE) DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.loanapplication.postgres");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaProperties(properties);

        return em;
    }

    @Bean(name = POSTGRES_TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(@Qualifier(POSTGRES_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier(POSTGRES_DATASOURCE) DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("datasql/schema.sql"));
        populator.addScript(new ClassPathResource("datasql/inventory-init.sql"));

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean(name = POSTGRES_ENTITY_MANAGER)
    public SharedEntityManagerBean sharedEntityManager(@Qualifier(POSTGRES_ENTITY_MANAGER_FACTORY) EntityManagerFactory emf) {
        SharedEntityManagerBean sem = new SharedEntityManagerBean();
        sem.setEntityManagerFactory(emf);
        return sem;
    }

}
