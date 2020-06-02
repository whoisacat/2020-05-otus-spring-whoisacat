package com.whoisacat.edu.testingApp1.service;

import com.whoisacat.edu.testingApp1.dao.IQuestionDao;
import com.whoisacat.edu.testingApp1.dao.QustionDaoSimple;
import com.whoisacat.edu.testingApp1.domain.Question;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceTest{

    public static final String TWO_QUESTIONS = "2questions";
    public static final String THREE_QUESTIONS = "3questions";
    Question[] questions1;
    Question[] questions2;
    IQuestionDao dao;

    @Before
    public void before(){
        questions1 = new Question[]{new Question(1,"who is there"),
                new Question(2,"is it you")};
        questions2 = new Question[]{new Question(1,"who is there"),
                new Question(2,"hello-hello..."),
                new Question(3,"is it you")};
        dao = mock(QustionDaoSimple.class);
        when(dao.loadObjectList(TWO_QUESTIONS))
                .thenReturn(Arrays.asList(questions1));
        when(dao.loadObjectList(THREE_QUESTIONS))
                .thenReturn(Arrays.asList(questions2));
    }

    @Test
    public void readList(){

        QuestionService service = new QuestionService(dao,TWO_QUESTIONS);
        List<Question> list = service.readList();
        assertEquals(2,list.size());
        assertEquals(list,Arrays.asList(questions1));
        assertNotSame(list,Arrays.asList(questions1));

        service = new QuestionService(dao,THREE_QUESTIONS);
        list = service.readList();
        assertEquals(3,list.size());
        assertEquals(list,Arrays.asList(questions2));
        assertNotSame(list,Arrays.asList(questions2));

    }
}
