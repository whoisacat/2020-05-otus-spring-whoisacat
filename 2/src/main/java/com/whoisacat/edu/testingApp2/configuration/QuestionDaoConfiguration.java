package com.whoisacat.edu.testingApp2.configuration;

import com.whoisacat.edu.testingApp2.dao.QuestionDao;
import com.whoisacat.edu.testingApp2.dao.QuestionDaoCsv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionDaoConfiguration{

    @Value("${com.whoisacat.edu.testingApp2.filename}")
    private String fileName;

    @Bean
    public QuestionDao questionDao() {
        return new QuestionDaoCsv(fileName);
    }

}
