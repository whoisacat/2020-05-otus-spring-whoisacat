package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.VisitingPlace;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.VisitingPlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override public List<VisitingPlaceDTO> toDTO(List<VisitingPlace> visitingPlaces) {
        return visitingPlaces
                .stream()
                .map(o -> new VisitingPlaceDTO(o.getCountry(), o.getCity(), o.getStreet(), o.getHouse(), o.getOrient()))
                .collect(Collectors.toList());
    }
}
