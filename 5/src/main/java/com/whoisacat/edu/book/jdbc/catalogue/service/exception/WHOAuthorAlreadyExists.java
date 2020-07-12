package com.whoisacat.edu.book.jdbc.catalogue.service.exception;

public class WHOAuthorAlreadyExists extends WHORequestClientException{

    public WHOAuthorAlreadyExists(){
        super("authorAlreadyExists");
    }
}
