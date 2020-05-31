package com.whoisacat.edu.testingApp1.service;

import com.whoisacat.edu.testingApp1.dao.IQuestionDao;
import com.whoisacat.edu.testingApp1.domain.Question;

import java.util.List;

public class QuestionService implements IQuestionService{

    private IQuestionDao dao;
    private String fileName;

    public QuestionService(){
    }

    public List<Question> readList(){
        return dao.loadObjectList(fileName);
    }

    public IQuestionDao getDao(){
        return dao;
    }

    public void setDao(IQuestionDao dao){
        this.dao = dao;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
