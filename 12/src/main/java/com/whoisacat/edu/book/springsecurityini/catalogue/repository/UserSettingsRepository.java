package com.whoisacat.edu.book.springsecurityini.catalogue.repository;

import com.whoisacat.edu.book.springsecurityini.catalogue.domain.User;
import com.whoisacat.edu.book.springsecurityini.catalogue.domain.UserSettings;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserSettingsRepository extends PagingAndSortingRepository<UserSettings,Long>{

    UserSettings findByUserUsername(String username);
}
