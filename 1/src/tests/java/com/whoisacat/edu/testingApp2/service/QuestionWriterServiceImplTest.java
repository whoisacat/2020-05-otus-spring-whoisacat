package com.whoisacat.edu.testingApp2.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.testingApp1.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp1.domain.Question;
import com.whoisacat.edu.testingApp1.service.QuestionReaderService;
import com.whoisacat.edu.testingApp1.service.QuestionWriterService;
import com.whoisacat.edu.testingApp1.service.QuestionWriterServiceImpl;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionWriterServiceImplTest{

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void test() throws MyCsvReaderException {
        QuestionReaderService reader = mock(QuestionReaderService.class);
        when(reader.readList())
                .thenReturn(Lists.newArrayList(new Question(1,"test1"),new Question(2,"test2")));

        PrintStream testPrinter = new PrintStream(outContent);

        QuestionWriterService writerService = new QuestionWriterServiceImpl(reader,testPrinter);
        writerService.writeQuestionsList();
        assertEquals("test1\ntest2\n", outContent.toString());
    }
}
