package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCustomRepository {

    Page<BookAndUserDTO> getBooks(Pageable pageable, String username, String text, boolean own);

    Page<BookDTO> getOwnBooks(String email, Pageable pageable);
}
