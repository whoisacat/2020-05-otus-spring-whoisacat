package com.whoisacat.edu.book.springsecurityini.catalogue.domain;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class Author implements Titled{

    @Id
    @SequenceGenerator(name="author_seq", sequenceName = "author_seq",allocationSize = 1)
    @GeneratedValue(generator="author_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    public Author(Long id,String title){
        this.id = id;
        this.title = title;
    }

    public Author(){
    }
    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
}
