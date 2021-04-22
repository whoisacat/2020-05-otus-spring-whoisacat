package com.whoisacat.edu.book.springactuatorini.catalogue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.whoisacat.edu.book.springactuatorini.catalogue.domain.User;
import com.whoisacat.edu.book.springactuatorini.catalogue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WHOUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @HystrixCommand(commandKey = "loadUserByUsername")
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        WHOUserPrincipal whoUserPrincipal = new WHOUserPrincipal(user);
        return whoUserPrincipal;
    }
}
