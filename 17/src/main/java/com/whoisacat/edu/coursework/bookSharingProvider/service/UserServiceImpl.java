package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.ROLES;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Role;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.EditUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.UserRegistrationDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.UserRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.security.WHOUserPrincipal;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserAlreadyExistException;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.UserNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserSettingsService userSettingsService;
    private final VisitingPlaceService visitingPlacesService;

    public UserServiceImpl(UserRepository repository,
            PasswordEncoder passwordEncoder,
            @Lazy UserSettingsService userSettingsService,
            @Lazy VisitingPlaceService visitingPlacesService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userSettingsService = userSettingsService;
        this.visitingPlacesService = visitingPlacesService;
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDTO userRegistrationDto) throws UserAlreadyExistException {

        emailExist(userRegistrationDto.getEmail()).ifPresent(UserAlreadyExistException::new);
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setEmail(userRegistrationDto.getEmail());
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

    @Transactional(readOnly = true)
    @Override public User getCurrentUser() {
        return repository.findByEmail(getUsernameFromSecurityContext()).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override public EditUserDTO getEditUserDTO() {
        User user = repository.findByEmail(getUsernameFromSecurityContext()).orElseThrow(UserNotFoundException::new);
        List<VisitingPlaceDTO> places = visitingPlacesService.toDTO(visitingPlacesService.getCurrentUserVisitingPlaces());
        return new EditUserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), places, "readonly");
    }

    @Override public User save(User user) {
        return repository.save(user);
    }

    @Override public User getByBook(Book book) {
        return repository.findByBooksContaining(book);
    }
}
