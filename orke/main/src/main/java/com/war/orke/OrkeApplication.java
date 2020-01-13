package com.war.orke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@PropertySource(value={"classpath:h2_datasource_application.properties"})
@SpringBootApplication(scanBasePackages = {"com.war.orke"})
@EntityScan(basePackages = {"com.war.orke.entity"})
@EnableJpaRepositories(basePackages = {"com.war.orke.repository"})
@EnableScheduling
//@EnableDiscoveryClient
public class OrkeApplication {

    private static final Logger LOG = LoggerFactory.getLogger(OrkeApplication.class);

    public static void main(String[] args) {
        LOG.info("Orke Application starts...");
        start();
    }

    public static void start() {
        SpringApplication.run(OrkeApplication.class);
    }

}
