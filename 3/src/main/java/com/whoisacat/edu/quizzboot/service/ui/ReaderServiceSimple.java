package com.whoisacat.edu.quizzboot.service.ui;

import com.whoisacat.edu.quizzboot.service.annotations.IOMethod;
import com.whoisacat.edu.quizzboot.service.annotations.Translate;

import java.io.InputStream;
import java.util.Scanner;

public class ReaderServiceSimple implements ReaderService{

    private final InputStream inputStream;

    public ReaderServiceSimple(InputStream inputStream){
        this.inputStream = inputStream;
    }

    @Translate(ioMethod = IOMethod.IN)
    @Override
    public String readString(){
        Scanner in = new Scanner(inputStream);
        return in.nextLine();
    }
}
