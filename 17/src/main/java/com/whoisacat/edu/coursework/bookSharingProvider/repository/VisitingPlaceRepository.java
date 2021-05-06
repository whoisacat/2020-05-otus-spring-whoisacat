package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.VisitingPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitingPlaceRepository extends CrudRepository<VisitingPlace, Long>{

    @Query(value = "select vp.city from visiting_place vp left join who_user wu on wu.id = vp.who_user_id " +
                   " where wu.email like :email ",
            nativeQuery = true)
    List<String> getCitiesByUsername(@Param("email") String username);
}
