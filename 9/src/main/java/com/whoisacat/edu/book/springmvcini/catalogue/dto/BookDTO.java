package com.whoisacat.edu.book.springmvcini.catalogue.dto;

public class BookDTO{

    private final String id;
    private final String title;
    private final String authorId;
    private final String authorTitle;
    private final String genreId;
    private final String genreTitle;

    public BookDTO(String id,
                   String title,
                   String authorId,
                   String authorTitle,
                   String genreId,
                   String genreTitle) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorTitle = authorTitle;
        this.genreId = genreId;
        this.genreTitle = genreTitle;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthorId(){
        return authorId;
    }

    public String getAuthorTitle(){
        return authorTitle;
    }

    public String getGenreId(){
        return genreId;
    }

    public String getGenreTitle(){
        return genreTitle;
    }
}
