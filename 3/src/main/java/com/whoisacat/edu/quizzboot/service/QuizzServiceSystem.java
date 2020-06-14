package com.whoisacat.edu.quizzboot.service;

import com.whoisacat.edu.Localization;
import com.whoisacat.edu.quizzboot.dao.WHOCsvReaderException;
import com.whoisacat.edu.quizzboot.domain.Question;
import com.whoisacat.edu.quizzboot.service.ui.IOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizzServiceSystem implements QuizzService{

    private final QuizzReaderService quizzReader;
    private final IOService ioService;

    @Autowired private Localization localization1;

    public QuizzServiceSystem(QuizzReaderService quizzReader,
                              IOService ioService){
        this.quizzReader = quizzReader;
        this.ioService = ioService;
    }

    @Override
    public void run(){
        List<Question> questions = getQuestions();
        int result = 0;
        for(Question question : questions){
            result += ascQuestion(question);
        }
        ioService.printString(getOpeningPhrase(result));
        ioService.printString("answer.result.is",result);
        ioService.printLine(getFinalPhrase(result));
    }

    private List<Question> getQuestions(){
        List<Question> questions = new ArrayList<>();
        try{
            questions.addAll(quizzReader.readList());
        } catch(WHOCsvReaderException e){
            System.out.println(e.getMessage());
        }
        return questions;
    }

    private int ascQuestion(Question question){
        ioService.printLine(question.getQuestion());
        String answer = ioService.readString();
        if(answer.equals(question.getAnswer())) return 1;
        return 0;
    }

    private String getOpeningPhrase(int result){
        switch(result){
            case 0:case 1:case 2:case 3:
                return "answer.bad.work";
            case 4:
                return "answer.nice.work";
            case 5:
                return "answer.perfect";
            default:
                return "answer.unknown.result";
        }
    }

    private String getFinalPhrase(int result){
        switch(result){
            case 0:case 1:case 2:case 3:
                return "answer.are.you.sure.you.are.web.developer";
            case 4:
                return "answer.you.can.do.better";
            case 5:
                return "answer.keep.it.up";
            default:
                return "answer.unknown.result";
        }
    }
}
