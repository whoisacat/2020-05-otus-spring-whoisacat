package com.whoisacat.edu.testingApp1.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.whoisacat.edu.testingApp1.domain.Question;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class QustionDaoSimple implements IQuestionDao{

    public List<Question> loadObjectList(String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            InputStream file = new ClassPathResource(fileName).getInputStream();
            MappingIterator<Question> readValues =
                    mapper.reader(Question.class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
