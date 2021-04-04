package com.whoisacat.edu.book.batch.catalogue.service.exception;

public class WHOAuthorAlreadyExistsException extends WHORequestClientException{

    public WHOAuthorAlreadyExistsException(){
        super("authorAlreadyExists");
    }
}
