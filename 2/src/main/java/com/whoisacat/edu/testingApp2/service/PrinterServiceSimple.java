package com.whoisacat.edu.testingApp2.service;

import java.io.PrintStream;

public class PrinterServiceSimple implements PrinterService{

    private final PrintStream printStream;

    public PrinterServiceSimple(PrintStream printStream){
        if(printStream == null){
            this.printStream = System.out;
        } else {
            this.printStream = printStream;
        }
    }

    @Override
    public void writeLine(String question){
        printStream.println(question);
    }
}
