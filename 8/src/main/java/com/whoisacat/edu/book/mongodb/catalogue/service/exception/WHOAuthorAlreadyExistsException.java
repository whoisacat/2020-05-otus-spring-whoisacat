package com.whoisacat.edu.book.mongodb.catalogue.service.exception;

public class WHOAuthorAlreadyExistsException extends WHORequestClientException{

    public WHOAuthorAlreadyExistsException(){
        super("authorAlreadyExists");
    }
}
