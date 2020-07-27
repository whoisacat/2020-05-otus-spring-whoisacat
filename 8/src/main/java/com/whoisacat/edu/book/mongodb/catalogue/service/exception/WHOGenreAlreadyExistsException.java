package com.whoisacat.edu.book.mongodb.catalogue.service.exception;

public class WHOGenreAlreadyExistsException extends WHORequestClientException{

    public WHOGenreAlreadyExistsException(){
        super("genreAlreadyExists");
    }
}
