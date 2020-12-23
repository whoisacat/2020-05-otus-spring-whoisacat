package com.whoisacat.edu.book.jpa.catalogue.domain;

public class BookDTO implements Titled{

    private Long id;

    private String title;

    private String author;

    private String genre;

    public BookDTO(){
    }

    public BookDTO(Long id,String title,String author,String genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getGenre(){
        return genre;
    }
}
