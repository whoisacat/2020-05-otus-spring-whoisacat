package com.whoisacat.edu.demochat.repository;

import com.whoisacat.edu.demochat.domain.ChatUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ChatUserRepositoryTest {

    @Autowired ChatUserRepository chatUserRepository;

    @Test
    public void mustSetIdOnInsert() {

        ChatUser chatUser = new ChatUser();
        chatUser.setEmail("email@email.email");
        chatUser.setTitle("title");
        Mono<ChatUser> chatUserMono = chatUserRepository.insert(chatUser);

        StepVerifier
                .create(chatUserMono)
                .assertNext(cu -> assertNotNull(cu.getId()))
                .expectComplete()
                .verify();
        chatUserMono.subscribe(chatUserRepository::delete);
    }
}
