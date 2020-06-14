package com.whoisacat.edu.quizzboot.service.ui;

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
    public void writeString(String string,int... args){
        printStream.print(string);
    }
}
