package com.whoisacat.edu.testingApp2.configuration;

import com.whoisacat.edu.testingApp2.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionServicesConfiguration{

    @Bean
    public PrinterService questionWriter() {
        return new PrinterServiceSimple(System.out);
    }

    @Bean
    public ReaderService readerService() {
        return new ReaderServiceSimple(System.in);
    }
}
