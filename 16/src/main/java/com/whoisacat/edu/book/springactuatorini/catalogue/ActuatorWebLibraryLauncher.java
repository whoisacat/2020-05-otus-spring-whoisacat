package com.whoisacat.edu.book.springactuatorini.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCircuitBreaker
@SpringBootApplication
public class ActuatorWebLibraryLauncher {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorWebLibraryLauncher.class,args);
    }
}
