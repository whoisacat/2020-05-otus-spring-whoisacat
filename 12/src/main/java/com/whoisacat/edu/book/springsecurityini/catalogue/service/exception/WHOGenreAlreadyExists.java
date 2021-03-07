package com.whoisacat.edu.book.springsecurityini.catalogue.service.exception;

public class WHOGenreAlreadyExists extends WHORequestClientException{

    public WHOGenreAlreadyExists(){
        super("genreAlreadyExists");
    }
}
