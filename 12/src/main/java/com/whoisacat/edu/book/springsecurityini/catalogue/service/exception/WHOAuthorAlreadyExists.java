package com.whoisacat.edu.book.springsecurityini.catalogue.service.exception;

public class WHOAuthorAlreadyExists extends WHORequestClientException{

    public WHOAuthorAlreadyExists(){
        super("authorAlreadyExists");
    }
}
