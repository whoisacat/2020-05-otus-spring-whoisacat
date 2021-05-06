package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.repository.VisitingPlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitingPlaceServiceImpl
        implements VisitingPlaceService {

    private final UserService userService;
    private final VisitingPlaceRepository repository;

    public VisitingPlaceServiceImpl(UserService userService,
            VisitingPlaceRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    @Override public List<String> getCurrentUserVisitingPlacesCities() {
        String username = userService.getUsernameFromSecurityContext();
        return repository.getCitiesByUsername(username);
    }
}
