package com.whoisacat.edu.coursework.bookSharingProvider.service.exception;

public class WHOBookAlreadyExists extends WHORequestClientException{

    public WHOBookAlreadyExists(){
        super("bookAlreadyExists");
    }
}
