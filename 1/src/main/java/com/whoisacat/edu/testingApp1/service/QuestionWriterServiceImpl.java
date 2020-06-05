package com.whoisacat.edu.testingApp1.service;

import com.whoisacat.edu.testingApp1.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp1.domain.Question;

import java.io.PrintStream;
import java.util.List;

public class QuestionWriterServiceImpl implements QuestionWriterService{

    private final QuestionReaderService reader;
    private final PrintStream printStream;

    public QuestionWriterServiceImpl(QuestionReaderService reader,PrintStream printStream){
        if(printStream == null){
            this.printStream = System.out;
        } else this.printStream = printStream;
        this.reader = reader;
    }

    @Override
    public void writeQuestionsList(){
        try{
            List<Question> questions = reader.readList();
            for(Question question : questions){
                printStream.println(question.getQuestion());
            }
        }catch(MyCsvReaderException e){
            printStream.println(e.getMessage());
        }
    }
}
