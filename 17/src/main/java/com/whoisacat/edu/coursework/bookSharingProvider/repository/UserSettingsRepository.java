package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.UserSettings;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserSettingsRepository extends PagingAndSortingRepository<UserSettings,Long>{

    Optional<UserSettings> findByUserEmail(String email);
}
