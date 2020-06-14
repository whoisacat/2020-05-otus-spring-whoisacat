package com.whoisacat.edu.quizzboot.service;

import com.whoisacat.edu.quizzboot.dao.QuestionDao;
import com.whoisacat.edu.quizzboot.dao.QuestionDaoCsv;
import com.whoisacat.edu.quizzboot.dao.WHOCsvReaderException;
import com.whoisacat.edu.quizzboot.domain.Question;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizzReaderServiceSimpleTest{

    private static Question[] questions1;
    private static Question[] questions2;
    private QuestionDao dao;

    @BeforeAll
    public static void before(){
        questions1 = new Question[]{new Question(1,"who is there","l"),
                new Question(2,"is it you","l")};
        questions2 = new Question[]{new Question(1,"who is there","l"),
                new Question(2,"hello-hello...","l"),
                new Question(3,"is it you","l")};
    }

    @Test
    public void readListTest() throws WHOCsvReaderException{

        dao = mock(QuestionDaoCsv.class);
        when(dao.loadObjectList())
                .thenReturn(Arrays.asList(questions1));
        QuizzReaderService service = new QuizzReaderServiceSimple(dao);
        List<Question> list = service.readList();
        assertEquals(2,list.size());
        assertEquals(list,Arrays.asList(questions1));
        assertNotSame(list,Arrays.asList(questions1));

        when(dao.loadObjectList())
                .thenReturn(Arrays.asList(questions2));
        service = new QuizzReaderServiceSimple(dao);
        list = service.readList();
        assertEquals(3,list.size());
        assertEquals(list,Arrays.asList(questions2));
        assertNotSame(list,Arrays.asList(questions2));

    }
}
