package com.whoisacat.edu.coursework.bookSharingProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCircuitBreaker
@SpringBootApplication
public class BookSharingProviderLauncher {

    public static void main(String[] args) {
        SpringApplication.run(BookSharingProviderLauncher.class,args);
    }
}
