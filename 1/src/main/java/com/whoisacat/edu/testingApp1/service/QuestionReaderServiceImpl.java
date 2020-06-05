package com.whoisacat.edu.testingApp1.service;

import com.whoisacat.edu.testingApp1.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp1.dao.QuestionDao;
import com.whoisacat.edu.testingApp1.domain.Question;

import java.util.List;

public class QuestionReaderServiceImpl implements QuestionReaderService{

    private final QuestionDao dao;

    public QuestionReaderServiceImpl(QuestionDao dao){
        this.dao = dao;
    }

    public List<Question> readList() throws MyCsvReaderException {
        return dao.loadObjectList();
    }
}
