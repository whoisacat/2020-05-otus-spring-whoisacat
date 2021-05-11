package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService extends NamedService<Book>{

    Page<Book> findAll(Pageable pageable);

    Book addBook(String book,String author,String genre);

    long getBooksCount();

    List<Book> findByAuthorId(long id);

    String buildBooksString(List<Book> existedBooks);

    Optional<Book> findById(long id);

    Book update(BookDTO book);

    void delete(long id);

    Page<BookAndUserDTO> findOtherPeoplesBooksInUsersCities(Pageable pageable, String text);

    void takeABookingRequest(Long id);

    Page<BookAndUserDTO> findOwnBooksInUsersCities(PageRequest pageable, String text);

    BookDetailDTO findBookDetailInfo(Long bookId);
}
