package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.VisitingPlace;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceRDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.VisitingPlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitingPlaceServiceImpl
        implements VisitingPlaceService {

    private final UserService userService;
    private final VisitingPlaceRepository repository;

    public VisitingPlaceServiceImpl(UserService userService,
            VisitingPlaceRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    @Transactional
    @Override public List<String> getCurrentUserVisitingPlacesCities() {
        String username = userService.getUsernameFromSecurityContext();
        return repository.getCitiesByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override public List<VisitingPlaceDTO> toDTO(List<VisitingPlace> visitingPlaces) {
        return visitingPlaces
                .stream()
                .map(o -> new VisitingPlaceDTO(o.getId(), o.getCountry(), o.getCity(), o.getStreet(), o.getHouse(), o.getOrient()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override public List<VisitingPlace> getCurrentUserVisitingPlaces() {
        return repository.getVisitingPlaceByUserEmail(userService.getUsernameFromSecurityContext());
    }

    @Transactional
    @Override public void insert(VisitingPlaceRDTO rdto) {
        VisitingPlace object = new VisitingPlace();
        object.setCountry(rdto.getCountry());
        object.setCity(rdto.getCity());
        object.setStreet(rdto.getStreet());
        object.setHouse(rdto.getHouse());
        object.setOrient(rdto.getOrient());
        object = repository.save(object);
        User user = userService.getCurrentUser();
        user.getVisitingPlaces().add(object);
        userService.save(user);
    }
}
