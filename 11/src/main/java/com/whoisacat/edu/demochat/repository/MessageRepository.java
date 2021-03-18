package com.whoisacat.edu.demochat.repository;

import com.whoisacat.edu.demochat.domain.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveMongoRepository<Message, String>{

    Flux<Message> findByChatId(String id);
}

