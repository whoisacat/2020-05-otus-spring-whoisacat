package com.whoisacat.edu.quizzboot.dao;

public class WHOCsvReaderException extends Exception{

    public WHOCsvReaderException(Exception e){
        super("somethingWrongWithCvReader",e);
    }
}
