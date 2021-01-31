package com.whoisacat.edu.book.springmvcini.catalogue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book")
public class Book implements Titled{

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "author",order = 3)
    private Author author;

    @Field(name = "genre",order = 4)
    private Genre genre;

    public Book(){
    }

    public Book(String id,String title,Author author,Genre genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title,Author author,Genre genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Author getAuthor(){
        return author;
    }

    public Genre getGenre(){
        return genre;
    }

    @Override public String toString(){
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
