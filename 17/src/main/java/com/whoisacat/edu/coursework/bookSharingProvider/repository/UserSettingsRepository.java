package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.UserSettings;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserSettingsRepository extends PagingAndSortingRepository<UserSettings,Long>{

    UserSettings findByUserUsername(String username);
}
