package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCustomRepository {

    Page<BookAndUserDTO> getBooksInUsersCities(Pageable pageable, String username, String text, boolean own);
}
