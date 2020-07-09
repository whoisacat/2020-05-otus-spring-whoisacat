package com.whoisacat.edu.quizzboot.service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.quizzboot.dao.WHOCsvReaderException;
import com.whoisacat.edu.quizzboot.domain.Question;
import com.whoisacat.edu.quizzboot.service.ui.PrinterService;
import com.whoisacat.edu.quizzboot.service.ui.PrinterServiceSimple;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrinterServiceSimpleTest{

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void test() throws WHOCsvReaderException{
        QuizzReaderServiceSimple reader = mock(QuizzReaderServiceSimple.class);
        when(reader.readList())
                .thenReturn(Lists.newArrayList(
                        new Question(1,"test1","k"),
                        new Question(2,"test2","k")));

        PrintStream testPrinter = new PrintStream(outContent);

        PrinterService writerService = new PrinterServiceSimple(testPrinter);
        writerService.writeLine("test1\ntest2");
        assert ("test1\ntest2\n".equals(outContent.toString()));
    }
}
