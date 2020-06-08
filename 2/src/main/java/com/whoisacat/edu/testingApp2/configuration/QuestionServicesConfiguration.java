package com.whoisacat.edu.testingApp2.configuration;

import com.whoisacat.edu.testingApp2.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionServicesConfiguration{

    private final ApplicationContext appContext;

    public QuestionServicesConfiguration(ApplicationContext appContext){
        this.appContext = appContext;
    }

    @Bean
    public PrinterService questionWriter() {
        return new PrinterServiceSimple(appContext.getBean(QuizzReaderService.class),System.out);
    }

    @Bean
    public ReaderService readerService() {
        return new ReaderServiceSimple(System.in);
    }
}
