package com.whoisacat.edu.book.jdbc.catalogue.service.exception;

public class WHOBookAlreadyExists extends WHORequestClientException{

    public WHOBookAlreadyExists(){
        super("bookAlreadyExists");
    }
}
