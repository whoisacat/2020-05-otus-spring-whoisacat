package com.whoisacat.edu.testingApp2.dao;

public class WHOCsvReaderException extends Exception{

    public WHOCsvReaderException(Exception e){
        super("somethingWrongWithCvReader",e);
    }
}
