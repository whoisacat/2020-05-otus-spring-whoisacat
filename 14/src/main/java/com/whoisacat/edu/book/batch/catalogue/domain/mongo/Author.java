package com.whoisacat.edu.book.batch.catalogue.domain.mongo;


import com.whoisacat.edu.book.batch.catalogue.domain.Titled;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "author")
public class Author implements Titled {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    public Author(String id,String title){
        this.id = id;
        this.title = title;
    }

    public Author(){
    }

    public Author(String title){
        this.title = title;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    @Override public String toString(){
        return "Author{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
