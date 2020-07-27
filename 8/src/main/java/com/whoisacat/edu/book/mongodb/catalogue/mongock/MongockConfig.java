package com.whoisacat.edu.book.mongodb.catalogue.mongock;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongockConfig{

    @Bean
    public SpringMongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, "otusBooks",
                "com.whoisacat.edu.book.mongodb.catalogue.mongock.changelog")
                .build();
    }
}
