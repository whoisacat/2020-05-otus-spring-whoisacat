package com.whoisacat.edu.book.catalogue.domain;

public class Genre implements Named{
    private final long id;
    private final String name;

    public Genre(long id,String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
