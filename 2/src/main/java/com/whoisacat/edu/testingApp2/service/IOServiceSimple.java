package com.whoisacat.edu.testingApp2.service;

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
    public void printString(String string){
        printer.writeLine(string);
    }

    @Override
    public String readString(){
        return reader.readString();
    }
}
