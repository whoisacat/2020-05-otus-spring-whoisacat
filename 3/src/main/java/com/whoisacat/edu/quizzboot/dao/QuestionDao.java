package com.whoisacat.edu.quizzboot.dao;

import com.whoisacat.edu.quizzboot.domain.Question;

import java.util.List;

public interface QuestionDao{

    List<Question> loadObjectList() throws WHOCsvReaderException;
}
