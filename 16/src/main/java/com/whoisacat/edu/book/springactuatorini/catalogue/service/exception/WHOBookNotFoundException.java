package com.whoisacat.edu.book.springactuatorini.catalogue.service.exception;

public class WHOBookNotFoundException extends WHORequestClientException{

    public WHOBookNotFoundException(){
        super("bookNotFound");
    }
}
