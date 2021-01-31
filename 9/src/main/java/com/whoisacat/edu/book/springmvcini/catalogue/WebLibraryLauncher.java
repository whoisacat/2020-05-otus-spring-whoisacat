package com.whoisacat.edu.book.springmvcini.catalogue;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories(basePackages = "com.whoisacat.edu.book.springmvcini.catalogue.repository")
public class WebLibraryLauncher{

    public static void main(String[] args) {
        SpringApplication.run(WebLibraryLauncher.class,args);
    }
}
