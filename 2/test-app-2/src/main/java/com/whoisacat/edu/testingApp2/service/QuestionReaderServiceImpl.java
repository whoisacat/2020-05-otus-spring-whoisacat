package com.whoisacat.edu.testingApp2.service;

import com.whoisacat.edu.testingApp2.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp2.dao.QuestionDao;
import com.whoisacat.edu.testingApp2.domain.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionReaderServiceImpl implements QuestionReaderService{

    private final QuestionDao dao;

    public QuestionReaderServiceImpl(QuestionDao questionDao){
        this.dao = questionDao;
    }

    public List<Question> readList() throws MyCsvReaderException {
        return dao.loadObjectList();
    }
}
