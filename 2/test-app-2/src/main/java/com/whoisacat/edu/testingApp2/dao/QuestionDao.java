package com.whoisacat.edu.testingApp2.dao;

import com.whoisacat.edu.testingApp2.domain.Question;

import java.util.List;

public interface QuestionDao{

    List<Question> loadObjectList() throws MyCsvReaderException;
}
