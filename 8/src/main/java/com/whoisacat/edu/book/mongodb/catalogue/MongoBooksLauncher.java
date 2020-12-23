package com.whoisacat.edu.book.mongodb.catalogue;

import com.whoisacat.edu.book.mongodb.catalogue.repository.AuthorMongoRepository;
import com.whoisacat.edu.book.mongodb.catalogue.repository.BookMongoRepository;
import com.whoisacat.edu.book.mongodb.catalogue.repository.CommentMongoRepository;
import com.whoisacat.edu.book.mongodb.catalogue.repository.GenreMongoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories(basePackages = "com.whoisacat.edu.book.mongodb.catalogue.repository")
public class MongoBooksLauncher{

    public static void main(String[] args) throws SQLException{
        ApplicationContext context = SpringApplication.run(MongoBooksLauncher.class,args);
    }
}
