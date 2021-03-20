package com.whoisacat.edu.book.batch.catalogue.service.exception;

public class WHOGenreAlreadyExistsException extends WHORequestClientException{

    public WHOGenreAlreadyExistsException(){
        super("genreAlreadyExists");
    }
}
