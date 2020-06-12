package com.whoisacat.edu.testingApp1.service;

import com.whoisacat.edu.testingApp1.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp1.domain.Question;

import java.util.List;

public interface QuestionReaderService{

    List<Question> readList() throws MyCsvReaderException;
}
