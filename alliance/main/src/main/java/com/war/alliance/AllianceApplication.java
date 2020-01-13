package com.war.alliance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.war.alliance"})
@EntityScan(basePackages = {"com.war.alliance.entity"})
@EnableJpaRepositories(basePackages = {"com.war.alliance.repository"})
@EnableDiscoveryClient
public class AllianceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllianceApplication.class, args);
    }
}
