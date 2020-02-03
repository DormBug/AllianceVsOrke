package com.war.orke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This is main class for module 'orke' that deploys on port and with using MariaDB, which properties are
 * in application.properties. {@link EnableAspectJAutoProxy} using there for including AOP. Now we have main
 * aspect com.war.orke.aspect.DtoValidatorAspect
 * */
@SpringBootApplication(scanBasePackages = {"com.war.orke"})
@EntityScan(basePackages = {"com.war.orke.entity"})
@EnableJpaRepositories(basePackages = {"com.war.orke.repository"})
@EnableAspectJAutoProxy
@EnableScheduling
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
