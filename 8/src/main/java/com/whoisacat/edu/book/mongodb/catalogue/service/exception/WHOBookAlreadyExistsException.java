package com.whoisacat.edu.book.mongodb.catalogue.service.exception;

public class WHOBookAlreadyExistsException extends WHORequestClientException{

    public WHOBookAlreadyExistsException(){
        super("bookAlreadyExists");
    }
}
