package com.whoisacat.edu.testingApp2.service;

import com.whoisacat.edu.testingApp2.dao.WHOCsvReaderException;
import com.whoisacat.edu.testingApp2.domain.Question;

import java.io.PrintStream;
import java.util.List;

public class PrinterServiceSimple implements PrinterService{

    private final QuizzReaderService reader;
    private final PrintStream printStream;

    public PrinterServiceSimple(QuizzReaderService reader,PrintStream printStream){
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
        }catch(WHOCsvReaderException e){
            printStream.println(e.getMessage());
        }
    }

    @Override
    public void writeLine(String question){
        printStream.println(question);
    }
}
