package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDetailDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.BookService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserSettingsService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserSettingsNotFound;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOBookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;

@Controller("")
public class BookController {

    private final BookService bookService;
    private final UserSettingsService userSettingsService;
    private final UserService userService;

    public BookController(BookService bookService,
            UserSettingsService userSettingsService,
            UserService userService) {
        this.bookService = bookService;
        this.userSettingsService = userSettingsService;
        this.userService = userService;
    }

    @GetMapping("books/details")
    public String getDetails(Model model, @RequestParam(name = "id") Long bookId) {
        BookDetailDTO book = bookService.findBookDetailInfo(bookId);
        model.addAttribute("dto", book);
        return "details";
    }

    @GetMapping("/")
    public String findPage(Model model, @RequestParam(name = "search_text", required = false) String text,
            @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = 0;
        }
        Page<BookAndUserDTO> books = bookService.findOtherPeoplesBooksInUsersCities(PageRequest.of(page,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()), text);
        model.addAttribute("books", books);
        return "search";
    }

    @GetMapping("books/booking")
    public String bookBookById(Model model, @RequestParam(name = "search_text", required = false) String text,
            @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "id") Long bookId,
            RedirectAttributes redirectAttributes) {
        bookService.takeABookingRequest(bookId);
        model.addAttribute("books", bookService.findOtherPeoplesBooksInUsersCities(PageRequest.of(page,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()), text));

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String utf8EncodedText = new String(bytes, StandardCharsets.UTF_8);
        redirectAttributes.addAttribute("search_text", text);
        redirectAttributes.addAttribute("page", page);
        return "redirect:/";
        //todo вообще, нужно открывать информацию о держателе и там бронировать
    }

    @GetMapping("books/my")
    public String listFirstPage(Model model) {
        Page<BookDTO> books = bookService.findOwnBooks(PageRequest.of(0,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        return "my_list";
    }

    @GetMapping("books/my/paged")
    public String listPage(Model model, @RequestParam("page") Integer pageNumber) {
        Page<BookDTO> books = bookService.findOwnBooks(
                PageRequest.of(pageNumber,
                        userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        if (books.getTotalPages() <= pageNumber) {
            return "redirect:/books/my/paged?page=" + (books.getTotalPages() - 1);
        }
        if(pageNumber == 0){
            return "redirect:/books/my";
        }
        return "my_list";
    }

    @GetMapping("books/edit")
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
        return "redirect:/books/my/";
    }

    @GetMapping("books/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.delete(id);
        return "redirect:/books/my/";
    }

    @GetMapping("books/addBook")
    public String insertBookPage() {
        return "newBook";
    }

    @PostMapping("books/addBook")
    public String insertBook(BookDTO dto) {
        bookService.addBook(dto.getTitle(),dto.getAuthorTitle(),dto.getGenreTitle());
        return "redirect:/books/my";
    }

    @Controller//todo заставить работать
    static class FaviconController {

        @GetMapping("favicon.ico")
        @ResponseBody
        void returnNoFavicon() {
        }
    }
}
