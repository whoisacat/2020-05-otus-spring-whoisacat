package com.whoisacat.edu.book.mongodb.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories(basePackages = "com.whoisacat.edu.book.mongodb.catalogue.repository")
public class MongoBooksLauncher{

    public static void main(String[] args) {
        SpringApplication.run(MongoBooksLauncher.class,args);
    }
}
