package com.whoisacat.edu.testingApp1.dao;

import com.whoisacat.edu.testingApp1.domain.Question;

import java.util.List;

public interface IQuestionDao{

    List<Question> loadObjectList(String fileName);
}
