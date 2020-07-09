package com.whoisacat.edu.quizzboot;

import com.whoisacat.edu.quizzboot.service.QuizzService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class,args);
        context.getBean(QuizzService.class).run();
    }
}
