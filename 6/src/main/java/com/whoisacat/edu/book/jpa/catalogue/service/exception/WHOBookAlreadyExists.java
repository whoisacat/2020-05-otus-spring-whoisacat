package com.whoisacat.edu.book.jpa.catalogue.service.exception;

public class WHOBookAlreadyExists extends WHORequestClientException{

    public WHOBookAlreadyExists(){
        super("bookAlreadyExists");
    }
}
