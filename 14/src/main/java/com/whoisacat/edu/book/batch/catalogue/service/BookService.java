package com.whoisacat.edu.book.batch.catalogue.service;

import com.whoisacat.edu.book.batch.catalogue.domain.mongo.Book;
import com.whoisacat.edu.book.batch.catalogue.domain.sql.BookSQL;

import java.util.List;

public interface BookService {

    BookSQL convert(Book book);
}
