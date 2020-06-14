package com.whoisacat.edu.quizzboot.service;

import com.whoisacat.edu.quizzboot.dao.QuestionDao;
import com.whoisacat.edu.quizzboot.dao.WHOCsvReaderException;
import com.whoisacat.edu.quizzboot.domain.Question;
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
