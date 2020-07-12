package com.whoisacat.edu.book.jpa.catalogue.service.exception;

public class WHOAuthorAlreadyExists extends WHORequestClientException{

    public WHOAuthorAlreadyExists(){
        super("authorAlreadyExists");
    }
}
