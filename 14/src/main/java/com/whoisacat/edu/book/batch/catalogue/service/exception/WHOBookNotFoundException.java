package com.whoisacat.edu.book.batch.catalogue.service.exception;

public class WHOBookNotFoundException extends WHORequestClientException{

    public WHOBookNotFoundException(){
        super("bookNotFound");
    }
}
