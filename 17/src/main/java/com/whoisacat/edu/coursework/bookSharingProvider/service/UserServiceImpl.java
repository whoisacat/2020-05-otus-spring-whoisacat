package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.ROLES;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Role;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.UserDto;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.UserRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.security.WHOUserPrincipal;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserAlreadyExistException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserSettingsService userSettingsService;

    public UserServiceImpl(UserRepository repository,
            PasswordEncoder passwordEncoder,
            @Lazy UserSettingsService userSettingsService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userSettingsService = userSettingsService;
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {

        emailExist(userDto.getEmail()).ifPresent(UserAlreadyExistException::new);
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        Role role = new Role();
        role.setRoleName(ROLES.ROLE_USER);
        user.getRoles().add(role);
        User newUser = repository.save(user);
        userSettingsService.create(user);
        return newUser;
    }

    private Optional<User> emailExist(String email) {
        return repository.findByEmail(email);
    }

    public String getUsernameFromSecurityContext() {
        SecurityContext ctxt = SecurityContextHolder.getContext();
        String username = ((WHOUserPrincipal) ctxt.getAuthentication().getPrincipal()).getUsername();
        return username;
    }
}
