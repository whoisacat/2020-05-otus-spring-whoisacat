package com.whoisacat.edu.book.springmvcini.catalogue.repository;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;

public interface BookRepositoryCustom {

    Book updateObject(Book book);

    Book createObject(Book book);

    Book findOne(int id);
}
