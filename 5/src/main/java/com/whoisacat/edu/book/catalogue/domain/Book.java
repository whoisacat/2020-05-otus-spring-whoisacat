package com.whoisacat.edu.book.catalogue.domain;

public class Book implements Named{
    private final Long id;
    private final String name;
    private final Author author;
    private final Genre genre;

    public Book(Long id,String name,Author author,Genre genre){
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Author getAuthor(){
        return author;
    }

    public Genre getGenre(){
        return genre;
    }
}
