package com.whoisacat.edu.quizzboot.service;


import com.whoisacat.edu.quizzboot.dao.WHOCsvReaderException;
import com.whoisacat.edu.quizzboot.domain.Question;

import java.util.List;

public interface QuizzReaderService{

    List<Question> readList() throws WHOCsvReaderException;
}
