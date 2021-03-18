package com.whoisacat.edu.demochat.handler;

import com.google.common.collect.Lists;
import com.whoisacat.edu.demochat.domain.Chat;
import com.whoisacat.edu.demochat.exceptions.ChatUserNotFoundException;
import com.whoisacat.edu.demochat.repository.ChatRepository;
import com.whoisacat.edu.demochat.repository.ChatUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class WHOChatHandler implements ChatHandler {

    private final ChatRepository repository;
    private final ChatUserRepository chatUserRepository;

    public WHOChatHandler(ChatRepository repository,
            ChatUserRepository chatUserRepository) {
        this.repository = repository;
        this.chatUserRepository = chatUserRepository;
    }

    @Override
    public Mono<ServerResponse> findOrInsertChat(ServerRequest request) {
        String userId = request.pathVariable("userId");
        List<String> listOfUsers = Lists.newArrayList(userId, request.pathVariable("interlocutorUserId"));
        return chatUserRepository.findAllById(listOfUsers).collectList()
                                 .flatMap(repository::existsByUsersContainingAndIsIndividualIsTrue)
                                 .flatMap(alreadyExists -> {
                                     if (alreadyExists) {
                                         return chatUserRepository.findAllById(
                                                 listOfUsers).collectList()
                                                                  .flatMap(
                                                                          repository::findByUsersContainingAndIsIndividualIsTrue);
                                     } else {
                                         Chat chat = new Chat();
                                         chat.setIsIndividual(true);
                                         chat.setLastChange(LocalDateTime.now());
                                         return chatUserRepository
                                                 .findAllById(listOfUsers)
                                                 .collectList().flatMap(list -> {
                                                     chat.setUsers(list);
                                                     chat.setTitle(list.stream()
                                                                       .filter(user -> user.getId().equals(request
                                                                               .pathVariable("interlocutorUserId")))
                                                                       .findAny()
                                                                       .orElseThrow(ChatUserNotFoundException::new)
                                                                       .getTitle());
                                                     return repository.insert(chat);
                                                 });
                                     }
                                 }).flatMap(chat -> ok().contentType(APPLICATION_JSON).body(chat, Chat.class));
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        repository.deleteById(request.pathVariable("id")).subscribe();
        return ServerResponse.ok().build();
    }
}
