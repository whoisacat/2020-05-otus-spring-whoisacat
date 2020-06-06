package com.whoisacat.edu.testingApp2;

import com.whoisacat.edu.testingApp2.service.QuestionWriterService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        QuestionWriterService service = context.getBean(QuestionWriterService.class);
        service.writeQuestionsList();
    }
}
