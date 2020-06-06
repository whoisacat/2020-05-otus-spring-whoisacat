package service;

import com.google.common.collect.Lists;
import com.whoisacat.edu.testingApp2.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp2.domain.Question;
import com.whoisacat.edu.testingApp2.service.PrinterService;
import com.whoisacat.edu.testingApp2.service.PrinterServiceSimple;
import com.whoisacat.edu.testingApp2.service.QuizzReaderServiceSimple;
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
        QuizzReaderServiceSimple reader = mock(QuizzReaderServiceSimple.class);
        when(reader.readList())
                .thenReturn(Lists.newArrayList(new Question(1,"test1","k"),new Question(2,"test2","k")));

        PrintStream testPrinter = new PrintStream(outContent);

        PrinterService writerService = new PrinterServiceSimple(reader,testPrinter);
        writerService.writeQuestionsList();
        assertEquals("test1\ntest2\n", outContent.toString());
    }
}
