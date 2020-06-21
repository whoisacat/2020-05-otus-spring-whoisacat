package com.whoisacat.edu.quizzboot.service.ui;

import org.springframework.stereotype.Service;

@Service
public class IOServiceSimple implements IOService{

    private final PrinterService printer;
    private final ReaderService reader;

    public IOServiceSimple(PrinterService printer,ReaderService reader){
        this.printer = printer;
        this.reader = reader;
    }

    @Override
    public void printLine(String string){
        printer.writeLine(string);
    }

    @Override
    public void printString(String string){
        printer.writeString(string);
    }

    @Override
    public void printString(String string, int result){
        printer.writeString(string, result);
    }

    @Override
    public String readString(){
        return reader.readString();
    }
}
