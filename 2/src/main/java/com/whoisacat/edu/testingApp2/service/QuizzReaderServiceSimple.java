package com.whoisacat.edu.testingApp2.service;

import com.whoisacat.edu.testingApp2.dao.WHOCsvReaderException;
import com.whoisacat.edu.testingApp2.dao.QuestionDao;
import com.whoisacat.edu.testingApp2.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizzReaderServiceSimple implements QuizzReaderService{

    private final QuestionDao dao;

    public QuizzReaderServiceSimple(QuestionDao questionDao){
        this.dao = questionDao;
    }

    public List<Question> readList() throws WHOCsvReaderException{
        return dao.loadObjectList();
    }
}
