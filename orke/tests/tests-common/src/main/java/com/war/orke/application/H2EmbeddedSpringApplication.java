package com.war.orke.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for test context running with in-memory H2 database. If we will be wanted to check our project
 * by working in another database or test it - we can easy change properties in application.properties file
 * and run our tests.
 * */
@SpringBootApplication(
        scanBasePackages = {"com.war.orke"},
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class
        })
@EntityScan(basePackages = {"com.war.orke.entity"})
@EnableJpaRepositories(basePackages = {"com.war.orke.repository"})
@EnableScheduling
public class H2EmbeddedSpringApplication {

    private static final Logger LOG = LoggerFactory.getLogger(H2EmbeddedSpringApplication.class);

    public static void main(String[] args) {
        LOG.info("Orke Application with H2 embedded database starts...");
        start();
    }

    public static void start() {
        SpringApplication.run(H2EmbeddedSpringApplication.class);
    }
}