package com.example.loanapplication.config;


import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CamundaH2Config {

    @Primary
    @Bean(name = "camundaDataSource")
    public DataSource camundaDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:camunda;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .driverClassName("org.h2.Driver")
                .username("camunda")
                .password("password")
                .build();
    }
}
