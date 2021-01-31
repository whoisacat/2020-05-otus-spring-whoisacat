package com.whoisacat.edu.book.springmvcini.catalogue.dto;

public class CommentDTO {

    private final String id;
    private final String title;
    private final String text;

    public CommentDTO(String id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override public String toString() {
        return "CommentDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
