package com.whoisacat.edu.book.springmvcini.catalogue.service.exception;

public class WHOGenreAlreadyExists extends WHORequestClientException{

    public WHOGenreAlreadyExists(){
        super("genreAlreadyExists");
    }
}
