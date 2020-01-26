package com.war.alliance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.war.alliance"})
@EnableReactiveMongoRepositories(basePackages = "com.war.alliance.repository")
public class AllianceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllianceApplication.class, args);
    }
}
