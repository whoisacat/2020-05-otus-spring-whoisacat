package com.whoisacat.edu.book.catalogue.service.exception;

public class WHOGenreAlreadyExists extends WHORequestClientException{

    public WHOGenreAlreadyExists(){
        super("genreAlreadyExists");
    }
}
