package com.whoisacat.edu.coursework.bookSharingProvider.service.exception;

public class WHOBookNotFoundException extends WHORequestClientException{

    public WHOBookNotFoundException(){
        super("bookNotFound");
    }
}
