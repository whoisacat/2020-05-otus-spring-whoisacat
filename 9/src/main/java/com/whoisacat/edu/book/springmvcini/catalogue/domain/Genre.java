package com.whoisacat.edu.book.springmvcini.catalogue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "genre")
public class Genre implements Titled{

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    public Genre(){
    }

    public Genre(String id,String title){
        this.id = id;
        this.title = title;
    }

    public Genre(String title){
        this.title = title;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    @Override public String toString(){
        return "Genre{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
