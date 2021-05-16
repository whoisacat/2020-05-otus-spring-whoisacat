package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("search/others")
    public ResponseEntity<Page<BookAndUserDTO>> findBooks(@RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(bookService.findOtherPeoplesBooksInUsersCities(PageRequest.of(page, size), text));
    }

    @GetMapping("search/own")
    public ResponseEntity<Page<BookAndUserDTO>> findOwnBooks(@RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
        return ResponseEntity.ok(bookService.findOwnBooks(PageRequest.of(page, size), text));
    }

    @PutMapping("booking")
    public ResponseEntity<Void> takeABookingRequest( @RequestParam(name = "id") Long id) {
        bookService.takeABookingRequest(id);
        return ResponseEntity.noContent().build();
    }
}
