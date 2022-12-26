package com.example.leftakias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories("com.example.leftakias.repository")
@EntityScan("com.example.leftakias.entity")
public class LeftakiasApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeftakiasApplication.class, args);
    }

}

@Configuration
@EnableTransactionManagement
class JpaConfiguration {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}