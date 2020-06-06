package com.whoisacat.edu.testingApp2.configuration;

import com.whoisacat.edu.testingApp2.service.QuestionReaderService;
import com.whoisacat.edu.testingApp2.service.QuestionWriterService;
import com.whoisacat.edu.testingApp2.service.QuestionWriterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionServicesConfiguration{

    @Autowired
    private ApplicationContext appContext;

    @Bean
    public QuestionWriterService questionWriter() {

        return new QuestionWriterServiceImpl(appContext.getBean(QuestionReaderService.class),System.out);
    }
}
