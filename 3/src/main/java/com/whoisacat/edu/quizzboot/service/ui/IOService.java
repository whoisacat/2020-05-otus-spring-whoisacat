package com.whoisacat.edu.quizzboot.service.ui;

public interface IOService{

    void printLine(String string);

    void printString(String string,int... args);

    String readString();
}
