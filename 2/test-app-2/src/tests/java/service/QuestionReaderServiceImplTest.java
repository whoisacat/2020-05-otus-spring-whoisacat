package service;

import com.whoisacat.edu.testingApp2.dao.MyCsvReaderException;
import com.whoisacat.edu.testingApp2.dao.QuestionDao;
import com.whoisacat.edu.testingApp2.dao.QuestionDaoCsv;
import com.whoisacat.edu.testingApp2.domain.Question;
import com.whoisacat.edu.testingApp2.service.QuizzReaderService;
import com.whoisacat.edu.testingApp2.service.QuizzReaderServiceSimple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionReaderServiceImplTest{

    public static final String TWO_QUESTIONS = "2questions";
    public static final String THREE_QUESTIONS = "3questions";
    Question[] questions1;
    Question[] questions2;
    QuestionDao dao;

    @Before
    public void before(){
        questions1 = new Question[]{new Question(1,"who is there","l"),
                new Question(2,"is it you","l")};
        questions2 = new Question[]{new Question(1,"who is there","l"),
                new Question(2,"hello-hello...","l"),
                new Question(3,"is it you","l")};
    }

    @Test
    public void readListTest() throws MyCsvReaderException {

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
