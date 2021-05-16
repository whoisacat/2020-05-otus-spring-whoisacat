package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.VisitingPlace;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceRDTO;

import java.util.List;

public interface VisitingPlaceService {

    List<String> getCurrentUserVisitingPlacesCities();

    List<VisitingPlaceDTO> toDTO(List<VisitingPlace> visitingPlaces);

    List<VisitingPlace> getCurrentUserVisitingPlaces();

    void insert(VisitingPlaceRDTO rdto);
}
