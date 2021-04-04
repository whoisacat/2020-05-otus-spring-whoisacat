package com.whoisacat.edu.book.springactuatorini.catalogue.service.exception;

public class WHOGenreAlreadyExists extends WHORequestClientException{

    public WHOGenreAlreadyExists(){
        super("genreAlreadyExists");
    }
}
