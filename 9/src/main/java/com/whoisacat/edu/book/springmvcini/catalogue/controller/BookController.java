package com.whoisacat.edu.book.springmvcini.catalogue.controller;

import com.whoisacat.edu.book.springmvcini.catalogue.domain.Book;
import com.whoisacat.edu.book.springmvcini.catalogue.dto.BookDTO;
import com.whoisacat.edu.book.springmvcini.catalogue.service.BookService;
import com.whoisacat.edu.book.springmvcini.catalogue.service.UserSettingsService;
import com.whoisacat.edu.book.springmvcini.catalogue.service.exception.WHOBookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class BookController{

    private final BookService bookService;
    private final UserSettingsService userSettingsService;

    public BookController(BookService bookService,
                          UserSettingsService userSettingsService) {
        this.bookService = bookService;
        this.userSettingsService = userSettingsService;
    }

    @GetMapping("/")
    public String getFirstPageList(Model model){
        Page<Book> books = bookService.findAll(PageRequest.of(0,userSettingsService.getUserSettings().getRowsPerPage()));
        model.addAttribute("books",books);
        return "list";
    }

    @GetMapping("/{pageNumber}")
    public String getDeterminedPageList(Model model,@PathVariable Integer pageNumber){
        Page<Book> books = bookService.findAll(PageRequest.of(pageNumber,userSettingsService.getUserSettings().getRowsPerPage()));
        model.addAttribute("books",books);
        if(pageNumber == 0){
            return "redirect:/";
        }
        return "list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") String id, Model model) {
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
    public String deleteBook(@RequestParam("id") String id) {
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

    @ControllerAdvice
    public class BookControllerAdviser {

        @ExceptionHandler(NullPointerException.class)
        public ModelAndView handleNPE(NullPointerException e) {
            ModelAndView m = new ModelAndView("err400");
            m.addObject("message", e.getMessage());
            m.addObject("timestamp", new Date());
            m.addObject("status", 400);
            m.setStatus(HttpStatus.BAD_REQUEST);
            return m;
        }
    }
}
