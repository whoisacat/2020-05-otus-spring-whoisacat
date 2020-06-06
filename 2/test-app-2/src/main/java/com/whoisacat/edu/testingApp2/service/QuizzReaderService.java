package com.whoisacat.edu.testingApp2.service;

import com.whoisacat.edu.testingApp2.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp2.domain.Question;

import java.util.List;

public interface QuizzReaderService{

    List<Question> readList() throws MyCsvReaderException;
}
