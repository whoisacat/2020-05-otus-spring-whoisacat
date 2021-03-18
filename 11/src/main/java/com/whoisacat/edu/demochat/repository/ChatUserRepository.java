package com.whoisacat.edu.demochat.repository;

import com.whoisacat.edu.demochat.domain.ChatUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatUserRepository extends ReactiveMongoRepository<ChatUser, String>{

    Mono<ChatUser> findById(String id);

    Mono<Boolean> existsByEmail(String email);

    Mono<ChatUser> findByEmail(String email);

    Flux<ChatUser> findAll();
}
