package com.whoisacat.edu.demochat.repository;

import com.whoisacat.edu.demochat.domain.Chat;
import com.whoisacat.edu.demochat.domain.ChatUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String>{

    Mono<Chat> findById(String id);

    Flux<Chat> findAllByUsersId(String id, Sort sort);

    Mono<Boolean> existsByUsersContainingAndIsIndividualIsTrue(List<ChatUser> chatUsers);

    Mono<Chat> findByUsersContainingAndIsIndividualIsTrue(List<ChatUser> chatUsers);
}

