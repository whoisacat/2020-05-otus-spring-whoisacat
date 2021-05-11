package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.UserRegistrationDTO;

public interface UserService {
    User registerNewUserAccount(UserRegistrationDTO userRegistrationDto);

    String getUsernameFromSecurityContext();

    User getCurrentUser();

    User save(User user);

    User getByBook(Book book);
}
