package com.whoisacat.edu.quizzboot.service.ui;

import com.whoisacat.edu.quizzboot.service.annotations.IOMethod;
import com.whoisacat.edu.quizzboot.service.annotations.Translate;

import java.io.PrintStream;

public class PrinterServiceSimple implements PrinterService{

    private final PrintStream printStream;

    public PrinterServiceSimple(PrintStream printStream){
        this.printStream = printStream;
    }

    @Translate(ioMethod = IOMethod.OUT)
    @Override
    public void writeString(String string){
        printStream.print(string);
    }

    @Translate(ioMethod = IOMethod.OUT)
    @Override
    public void writeString(String string, int result){
        printStream.print(string);
    }
}
