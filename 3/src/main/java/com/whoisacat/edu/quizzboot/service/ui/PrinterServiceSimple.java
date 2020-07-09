package com.whoisacat.edu.quizzboot.service.ui;

import java.io.PrintStream;

public class PrinterServiceSimple implements PrinterService{

    private final PrintStream printStream;

    public PrinterServiceSimple(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void writeString(String string){
        printStream.print(string);
    }

    @Override
    public void writeString(String string, int result){
        printStream.print(string);
    }
}
