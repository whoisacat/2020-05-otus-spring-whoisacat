package com.whoisacat.edu.coursework.bookSharingProvider.repository;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByBooksContaining(Book book);
}
