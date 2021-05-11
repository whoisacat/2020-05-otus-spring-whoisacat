package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDetailDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.BookService;
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

@Controller
public class BookController {

    private final BookService bookService;
    private final UserSettingsService userSettingsService;

    public BookController(BookService bookService,
                          UserSettingsService userSettingsService) {
        this.bookService = bookService;
        this.userSettingsService = userSettingsService;
    }

    @GetMapping("details")
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

    @GetMapping("booking")
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
//        return "search";
        //todo вообще, нужно открывать информацию о держателе
    }

    @GetMapping("/mememe")
    public String listFirstPage(Model model) {
        Page<Book> books = bookService.findAll(PageRequest.of(0,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        return "my_list";
    }

    @GetMapping("/mememe/{pageNumber}")
    public String listPage(Model model,@PathVariable Integer pageNumber) {
        Page<Book> books = bookService.findAll(PageRequest.of(pageNumber,
                userSettingsService.getUserSettings().orElseThrow(UserSettingsNotFound::new).getRowsPerPage()));
        model.addAttribute("books",books);
        if(pageNumber == 0){
            return "redirect:/mememe/";
        }
        return "my_list";
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
        return "redirect:/mememe/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.delete(id);
        return "redirect:/mememe/";
    }

    @GetMapping("/addBook")
    public String insertBookPage() {
        return "newBook";
    }

    @PostMapping("/addBook")
    public String insertBook(BookDTO dto) {
        bookService.addBook(dto.getTitle(),dto.getAuthorTitle(),dto.getGenreTitle());
        return "redirect:/mememe/";
    }

    @Controller
    static class FaviconController {

        @GetMapping("favicon.ico")
        @ResponseBody
        void returnNoFavicon() {
        }
    }
}
