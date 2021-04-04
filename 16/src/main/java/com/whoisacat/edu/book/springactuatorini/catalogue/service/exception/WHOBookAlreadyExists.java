package com.whoisacat.edu.book.springactuatorini.catalogue.service.exception;

public class WHOBookAlreadyExists extends WHORequestClientException{

    public WHOBookAlreadyExists(){
        super("bookAlreadyExists");
    }
}
