package com.whoisacat.edu.book.springdata.catalogue.service.exception;

public class WHOGenreAlreadyExists extends WHORequestClientException{

    public WHOGenreAlreadyExists(){
        super("genreAlreadyExists");
    }
}
