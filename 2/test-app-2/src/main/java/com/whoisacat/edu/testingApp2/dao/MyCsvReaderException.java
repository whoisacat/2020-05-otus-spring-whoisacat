package com.whoisacat.edu.testingApp2.dao;

public class MyCsvReaderException extends Exception{

    public MyCsvReaderException(Exception e){
        super("somethingWrongWithCvReader",e);
    }
}
