package com.whoisacat.edu.book.batch.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories(basePackages = "com.whoisacat.edu.book.batch.catalogue.repository.mongo")
public class BatchBooksLauncher {

    public static void main(String[] args) throws SQLException{
        ApplicationContext context = SpringApplication.run(BatchBooksLauncher.class,args);
    }
}
