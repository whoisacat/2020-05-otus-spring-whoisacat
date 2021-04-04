package com.whoisacat.edu.book.springactuatorini.catalogue.dto;

public class BookDTO{

    private final Long id;
    private final String title;
    private final Long authorId;
    private final String authorTitle;
    private final Long genreId;
    private final String genreTitle;

    public BookDTO(Long id,String title,Long authorId,String authorTitle,Long genreId,String genreTitle){
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorTitle = authorTitle;
        this.genreId = genreId;
        this.genreTitle = genreTitle;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Long getAuthorId(){
        return authorId;
    }

    public String getAuthorTitle(){
        return authorTitle;
    }

    public Long getGenreId(){
        return genreId;
    }

    public String getGenreTitle(){
        return genreTitle;
    }
}
