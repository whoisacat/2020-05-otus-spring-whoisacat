package com.whoisacat.edu.coursework.bookSharingProvider.dto;

import java.util.List;

public class BookDetailDTO {

    private final Long id;
    private final String title;
    private final String author;
    private final String genre;
    private final String user;
    private final String email;
    private final List<VisitingPlaceDTO> places;

    public BookDetailDTO(Long id, String title, String author, String genre, String user, String email,
            List<VisitingPlaceDTO> places) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.user = user;
        this.email = email;
        this.places = places;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public List<VisitingPlaceDTO> getPlaces() {
        return places;
    }
}
