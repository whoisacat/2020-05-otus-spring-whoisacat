package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.EditUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.BookService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserSettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final BookService bookService;
    private final UserSettingsService userSettingsService;
    private final UserService userService;

    public UserController(BookService bookService,
            UserSettingsService userSettingsService,
            UserService userService) {
        this.bookService = bookService;
        this.userSettingsService = userSettingsService;
        this.userService = userService;
    }

    @GetMapping("user/mememe")
    public String getUserInfo(Model model) {
        EditUserDTO editUserDTO = userService.getEditUserDTO();
        model.addAttribute("dto", editUserDTO);
        return "mememe";
    }
}
