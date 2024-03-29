package com.saadkasu.type_ahead_search_service.Client;

import com.saadkasu.type_ahead_search_service.Controllers.ISearchTermController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.saadkasu.type_ahead_search_service")
@ComponentScan(basePackages = "com.saadkasu.type_ahead_search_service")
@EnableJpaRepositories(basePackages = "com.saadkasu.type_ahead_search_service")
public class TypeAheadSearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TypeAheadSearchServiceApplication.class, args);
    }

}
