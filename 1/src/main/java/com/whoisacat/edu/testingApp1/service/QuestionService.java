package com.whoisacat.edu.testingApp1.service;

import com.whoisacat.edu.testingApp1.dao.IQuestionDao;
import com.whoisacat.edu.testingApp1.domain.Question;

import java.util.List;

public class QuestionService implements IQuestionService{

    private IQuestionDao dao;
    private String fileName;

    public QuestionService(IQuestionDao dao,String fileName){
        this.dao = dao;
        this.fileName = fileName;
    }

    public List<Question> readList(){
        return dao.loadObjectList(fileName);
    }
}
