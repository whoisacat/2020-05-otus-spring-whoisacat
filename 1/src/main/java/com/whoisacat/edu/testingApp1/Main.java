package com.whoisacat.edu.testingApp1;

import com.whoisacat.edu.testingApp1.domain.Question;
import com.whoisacat.edu.testingApp1.service.IQuestionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        IQuestionService questionService = context.getBean(IQuestionService.class);

        for(Question q : questionService.readList()){
            System.out.println(q.getQuestion());
        }
    }
}
