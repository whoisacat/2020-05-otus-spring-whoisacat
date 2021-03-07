package com.whoisacat.edu.book.springsecurityini.catalogue.service.exception;

public class WHOBookAlreadyExists extends WHORequestClientException{

    public WHOBookAlreadyExists(){
        super("bookAlreadyExists");
    }
}
