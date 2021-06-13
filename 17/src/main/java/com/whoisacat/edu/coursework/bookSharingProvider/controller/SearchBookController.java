package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.BookService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserSettingsService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserSettingsNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author whoisacat
 * @since 13.06.2021
 */
@Controller
public class SearchBookController {

    private final BookService bookService;
    private final UserSettingsService userSettingsService;

    public SearchBookController(BookService bookService,
            UserSettingsService userSettingsService) {
        this.bookService = bookService;
        this.userSettingsService = userSettingsService;
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

}
