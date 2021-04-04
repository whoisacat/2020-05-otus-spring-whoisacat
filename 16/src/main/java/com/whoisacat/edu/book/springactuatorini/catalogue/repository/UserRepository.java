package com.whoisacat.edu.book.springactuatorini.catalogue.repository;

import com.whoisacat.edu.book.springactuatorini.catalogue.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
