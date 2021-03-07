package com.whoisacat.edu.book.springsecurityini.catalogue.domain;

import javax.persistence.*;

@Entity
@Table(name = "genre",uniqueConstraints={@UniqueConstraint(columnNames={"title"})})
public class Genre implements Titled{

    @Id
    @SequenceGenerator(name="genre_seq", sequenceName = "genre_seq",allocationSize = 1)
    @GeneratedValue(generator="genre_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;

    public Genre(){
    }

    public Genre(Long id,String title){
        this.id = id;
        this.title = title;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
}
