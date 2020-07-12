package com.whoisacat.edu.testingApp1;

import com.whoisacat.edu.testingApp1.service.QuestionWriterService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionWriterService questionService = context.getBean(QuestionWriterService.class);

        questionService.writeQuestionsList();
    }
}
