package com.whoisacat.edu.quizzboot.service.ui;

import com.whoisacat.edu.quizzboot.service.annotations.IOMethod;
import com.whoisacat.edu.quizzboot.service.annotations.Translate;
import org.springframework.stereotype.Service;

@Service
public class IOServiceSimple implements IOService{

    private final PrinterService printer;
    private final ReaderService reader;

    public IOServiceSimple(PrinterService printer,ReaderService reader){
        this.printer = printer;
        this.reader = reader;
    }

    @Translate(ioMethod = IOMethod.OUT)
    @Override
    public void printLine(String string){
        printer.writeLine(string);
    }

    @Translate(ioMethod = IOMethod.OUT)
    @Override
    public void printString(String string){
        printer.writeString(string);
    }

    @Translate(ioMethod = IOMethod.OUT)
    @Override
    public void printString(String string, int result){
        printer.writeString(string, result);
    }

    @Translate(ioMethod = IOMethod.IN)
    @Override
    public String readString(){
        return reader.readString();
    }
}
