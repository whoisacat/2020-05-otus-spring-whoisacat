package com.whoisacat.edu.demochat.handler;

import com.whoisacat.edu.demochat.domain.Chat;
import com.whoisacat.edu.demochat.domain.ChatUser;
import com.whoisacat.edu.demochat.domain.Message;
import com.whoisacat.edu.demochat.repository.ChatRepository;
import com.whoisacat.edu.demochat.repository.ChatUserRepository;
import com.whoisacat.edu.demochat.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class WHOMessageHandler implements MessageHandler {

    private final MessageRepository repository;
    private final ChatUserRepository chatUserRepository;
    private final ChatRepository chatRepository;

    public WHOMessageHandler(MessageRepository repository,
            ChatUserRepository chatUserRepository, ChatRepository chatRepository) {
        this.repository = repository;
        this.chatUserRepository = chatUserRepository;
        this.chatRepository = chatRepository;
    }

    public Mono<ServerResponse> deleteMessage(ServerRequest request) {
        String messageId = request.pathVariable("messageId");
        repository.deleteById(messageId).subscribe();
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> getMessageByChatId(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                   .body(repository.findByChatId(request.pathVariable("chatId")), Message.class);
    }

    public Mono<ServerResponse> insertMessage(ServerRequest request) {
        return chatUserRepository.findById(request.pathVariable("chatUserId"))
                                 .zipWith(chatRepository.findById(request.pathVariable("chatId")))
                                 .map(tuple -> {
                                     ChatUser chatUser = tuple.getT1();
                                     Chat chat = tuple.getT2();
                                     LocalDateTime now = LocalDateTime.now();
                                     chat.setLastChange(now);
                                     Message message = new Message();
                                     message.setDateTime(now);
                                     message.setChat(chat);
                                     message.setAuthor(chatUser);
                                     request.queryParam("text").ifPresent(message::setText);
                                     return message;
                                 }).flatMap(repository::insert)
                                 .flatMap(message -> ok().contentType(APPLICATION_JSON).bodyValue(message));
    }
}
