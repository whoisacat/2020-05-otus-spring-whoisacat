package com.whoisacat.edu.book.springmvcini.catalogue.service.exception;

public class WHOBookNotFoundException extends WHORequestClientException{

    public WHOBookNotFoundException(){
        super("bookNotFound");
    }
}
