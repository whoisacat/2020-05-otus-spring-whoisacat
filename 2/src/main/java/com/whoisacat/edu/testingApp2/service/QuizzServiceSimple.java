package com.whoisacat.edu.testingApp2.service;

import com.whoisacat.edu.testingApp2.dao.WHOCsvReaderException;
import com.whoisacat.edu.testingApp2.domain.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizzServiceSimple implements QuizzService{

    private final QuizzReaderService quizzReader;
    private final IOService ioService;

    public QuizzServiceSimple(QuizzReaderService quizzReader,
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
        ioService.printString(constructAnswer(result).toString());
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
        ioService.printString(question.getQuestion());
        String answer = ioService.readString();
        if(answer.equals(question.getAnswer())) return 1;
        return 0;
    }

    private StringBuilder constructAnswer(int result){
        StringBuilder answer = new StringBuilder();
        switch(result){
            case 0:case 1:case 2:case 3:
                answer.append("Bad work, ")
                        .append(getResultStringBuilder(result))
                        .append(". Are you sure you are web developer?");
                break;
            case 4:
                answer.append("Nice work, ")
                        .append(getResultStringBuilder(result))
                        .append(". But you can do better.");
                break;
            case 5:
                answer.append("Perfect! ")
                        .append(getResultStringBuilder(result));
                break;
            default:
                answer.append("Unknown result. Tell me about this ;)");
        }
        return answer;
    }

    private StringBuilder getResultStringBuilder(int result){
        return new StringBuilder("result is:").append(result);
    }
}
