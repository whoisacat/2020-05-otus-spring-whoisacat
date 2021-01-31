package com.whoisacat.edu.book.springmvcini.catalogue.service;

import com.whoisacat.edu.book.springmvcini.catalogue.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findBookCommentsByBookTitle(String bookTitle, Integer page, Integer pageSize);

    List<CommentDTO> findBookCommentsByBookTitle(String bookTitle);

    List<CommentDTO> findBookCommentsByBookAuthorTitle(String bookTitle, Integer page, Integer pageSize);

    List<CommentDTO> findBookCommentsByBookAuthorTitle(String authorTitle);

    CommentDTO addComment(String title,String text,String bookTitle,String bookAuthor,String bookGenre);
}
