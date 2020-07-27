package com.whoisacat.edu.book.mongodb.catalogue.service;

import java.util.List;

public interface CommentService {

    List<String> findBookCommentsByBookTitle(String bookTitle,Integer page,Integer pageSize);

    List<String> findBookCommentsByBookTitle(String bookTitle);

    List<String> findBookCommentsByBookAuthorTitle(String bookTitle,Integer page,Integer pageSize);

    List<String> findBookCommentsByBookAuthorTitle(String authorTitle);

    void addComment(String title,String text,String bookTitle,String bookAuthor,String bookGenre);
}
