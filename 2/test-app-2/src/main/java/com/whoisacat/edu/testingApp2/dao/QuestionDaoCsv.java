package com.whoisacat.edu.testingApp2.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.whoisacat.edu.testingApp2.domain.Question;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

public class QuestionDaoCsv implements QuestionDao{

    private final String fileName;

    public QuestionDaoCsv(String fileName){
        this.fileName = fileName;
    }

    public List<Question> loadObjectList() throws MyCsvReaderException {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            InputStream file = new ClassPathResource(fileName).getInputStream();
            MappingIterator<Question> readValues =
                    mapper.reader(Question.class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            throw new MyCsvReaderException();
        }
    }
}
