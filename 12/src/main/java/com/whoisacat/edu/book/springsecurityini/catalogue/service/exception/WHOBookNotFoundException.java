package com.whoisacat.edu.book.springsecurityini.catalogue.service.exception;

public class WHOBookNotFoundException extends WHORequestClientException{

    public WHOBookNotFoundException(){
        super("bookNotFound");
    }
}
