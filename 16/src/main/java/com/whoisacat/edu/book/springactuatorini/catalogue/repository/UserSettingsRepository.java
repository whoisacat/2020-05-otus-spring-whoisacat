package com.whoisacat.edu.book.springactuatorini.catalogue.repository;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.UserSettings;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSettingsRepository extends PagingAndSortingRepository<UserSettings,Long>{

    UserSettings findByUserUsername(String username);
}
