package com.whoisacat.edu.coursework.bookSharingProvider.controller;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.UserRegistrationDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.service.UserService;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserAlreadyExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserRegistrationDTO userRegistrationDto = new UserRegistrationDTO();
        model.addAttribute("user", userRegistrationDto);
        return "registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserRegistrationDTO userRegistrationDto,
            HttpServletRequest request) {

        try {
            User registered = userService.registerNewUserAccount(userRegistrationDto);
        } catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("emailError");
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }

        return new ModelAndView("successRegister", "user", userRegistrationDto);
    }
}
