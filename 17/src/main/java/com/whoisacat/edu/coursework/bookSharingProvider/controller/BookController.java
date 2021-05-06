package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.BookService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserSettingsService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserSettingsNotFound;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOBookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;
    private final UserSettingsService userSettingsService;

    public BookController(BookService bookService,
                          UserSettingsService userSettingsService) {
        this.bookService = bookService;
        this.userSettingsService = userSettingsService;
    }

    @GetMapping("/booking/")
    public String findFirstPage(Model model) {
        Page<Book> books = bookService.findWithRelativePlaces(PageRequest.of(0,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        return "bookBookingPage";
    }

    @GetMapping("/booking/{pageNumber}")
    public String findPage(Model model,@PathVariable Integer pageNumber) {
        Page<Book> books = bookService.findWithRelativePlaces(PageRequest.of(pageNumber,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        if(pageNumber == 0){
            return "redirect:/booking/";
        }
        return "bookBookingPage";
    }

    @GetMapping("/")
    public String listFirstPage(Model model) {
        Page<Book> books = bookService.findAll(PageRequest.of(0,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        return "list";
    }

    @GetMapping("/{pageNumber}")
    public String listPage(Model model,@PathVariable Integer pageNumber) {
        Page<Book> books = bookService.findAll(PageRequest.of(pageNumber,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        if(pageNumber == 0){
            return "redirect:/";
        }
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(WHOBookNotFoundException::new);
        BookDTO dto = new BookDTO(book.getId(),book.getTitle(),book.getAuthor().getId(),book.getAuthor().getTitle(),
                book.getGenre().getId(),book.getGenre().getTitle());
        model.addAttribute("dto", dto);
        return "edit";
    }
    
    @PostMapping("/edit")
    public String saveBook(BookDTO dto,Model model) {
        Book book = bookService.update(dto);
        model.addAttribute(book);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/addBook")
    public String insertBookPage() {
        return "newBook";
    }

    @PostMapping("/addBook")
    public String insertBook(BookDTO dto) {
        bookService.addBook(dto.getTitle(),dto.getAuthorTitle(),dto.getGenreTitle());
        return "redirect:/";
    }

    @Controller
    static class FaviconController {

        @GetMapping("favicon.ico")
        @ResponseBody
        void returnNoFavicon() {
        }
    }
}
