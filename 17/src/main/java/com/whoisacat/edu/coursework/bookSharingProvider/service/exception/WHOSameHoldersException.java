package com.whoisacat.edu.coursework.bookSharingProvider.service.exception;

public class WHOSameHoldersException extends WHORequestClientException{

    public WHOSameHoldersException(){
        super("holdersMustBeDifferentUsers");
    }
}
