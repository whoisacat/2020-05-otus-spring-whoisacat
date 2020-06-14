package com.whoisacat.edu.quizzboot.service.ui;

public interface PrinterService{

    default void writeLine(String stringLine){
        writeString(stringLine);
        writeString("\n");
    }

    void writeString(String string,int... args);
}
