package com.whoisacat.edu.book.springmvcini.catalogue.service.exception;

public class WHOAuthorAlreadyExists extends WHORequestClientException{

    public WHOAuthorAlreadyExists(){
        super("authorAlreadyExists");
    }
}
