package com.example.loanapplication.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CamundaH2Config {

    @Value("${camunda.datasource.url}")
    private String url;

    @Value("${camunda.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${camunda.datasource.username}")
    private String username;

    @Value("${camunda.datasource.password}")
    private String password;

    @Primary
    @Bean(name = "camundaDataSource")
    public DataSource camundaDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .build();
    }
}
