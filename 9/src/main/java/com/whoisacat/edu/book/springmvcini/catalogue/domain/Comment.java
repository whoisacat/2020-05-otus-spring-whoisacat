package com.whoisacat.edu.book.springmvcini.catalogue.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
public class Comment implements Titled{

    @Id
    private String id;

    private String title;

    private String text;

    private Book book;

    public Comment(String id,String title,String text,Book book){
        this.id = id;
        this.title = title;
        this.text = text;
        this.book = book;
    }

    public Comment(String title,String text,Book book){
        this.title = title;
        this.text = text;
        this.book = book;
    }

    public Comment(){
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

    public Book getBook(){
        return book;
    }

    @Override public String toString(){
        return "Comment on book " + book.toString() +
                "\n title: " + title +
                "\n text: " + text;
    }
}
