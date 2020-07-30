package com.whoisacat.edu.book.springdata.catalogue.service.exception;

public class WHOAuthorAlreadyExists extends WHORequestClientException{

    public WHOAuthorAlreadyExists(){
        super("authorAlreadyExists");
    }
}
