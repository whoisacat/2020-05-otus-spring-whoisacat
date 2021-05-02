package com.whoisacat.edu.coursework.bookSharingProvider.service.exception;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;

public class UserAlreadyExistException
        extends WHORequestClientException {

    public UserAlreadyExistException(User user) {
        super("There is an account with that email address: " + user.getEmail());
    }
}
