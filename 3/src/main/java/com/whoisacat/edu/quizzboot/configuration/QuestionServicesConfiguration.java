package com.whoisacat.edu.quizzboot.configuration;

import com.whoisacat.edu.quizzboot.service.ui.PrinterService;
import com.whoisacat.edu.quizzboot.service.ui.PrinterServiceSimple;
import com.whoisacat.edu.quizzboot.service.ui.ReaderService;
import com.whoisacat.edu.quizzboot.service.ui.ReaderServiceSimple;
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
