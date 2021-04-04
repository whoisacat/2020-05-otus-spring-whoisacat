package com.whoisacat.edu.book.batch.catalogue.service.exception;

public class WHOBookAlreadyExistsException extends WHORequestClientException{

    public WHOBookAlreadyExistsException(){
        super("bookAlreadyExists");
    }
}
