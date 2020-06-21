package com.whoisacat.edu.testingApp2.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.whoisacat.edu.testingApp2.domain.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository
public class QuestionDaoCsv implements QuestionDao{

    private final String fileName;

    public QuestionDaoCsv(@Value("${com.whoisacat.edu.testingApp2.filename}") String fileName){
        this.fileName = fileName;
    }

    public List<Question> loadObjectList() throws WHOCsvReaderException{
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            InputStream file = new ClassPathResource(fileName).getInputStream();
            MappingIterator<Question> readValues =
                    mapper.reader(Question.class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            throw new WHOCsvReaderException(e);
        }
    }
}
