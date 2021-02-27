package com.whoisacat.edu.demochat.config;

import com.whoisacat.edu.demochat.domain.Chat;
import com.whoisacat.edu.demochat.domain.ChatUser;
import com.whoisacat.edu.demochat.handler.ChatHandler;
import com.whoisacat.edu.demochat.handler.ChatUserHandler;
import com.whoisacat.edu.demochat.handler.MessageHandler;
import com.whoisacat.edu.demochat.repository.ChatRepository;
import com.whoisacat.edu.demochat.repository.ChatUserRepository;
import com.whoisacat.edu.demochat.repository.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FunctionalEndpointConfigs {

    private final MessageHandler messageHandler;
    private final ChatHandler chatHandler;
    private final ChatUserHandler chatUserHandler;

    public FunctionalEndpointConfigs(MessageHandler messageHandler,
            ChatHandler chatHandler, ChatUserHandler chatUserHandler) {
        this.messageHandler = messageHandler;
        this.chatHandler = chatHandler;
        this.chatUserHandler = chatUserHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> composedChatUserRouters(ChatUserRepository repository) {
        return route()
                .POST("/chatUsers", accept(APPLICATION_JSON),
                        chatUserHandler::findOrInsert)
                .GET("/chatUsers", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(repository.findAll(), ChatUser.class))
                .DELETE("/chatUsers/{id}", accept(APPLICATION_JSON), chatUserHandler::delete)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> composedChatRouters(ChatRepository repository,
            ChatUserRepository chatUserRepository) {
        return route()
                .GET("/chats/{id}", request -> repository.findById(request.pathVariable("id"))
                                                         .flatMap(chat -> ok().contentType(APPLICATION_JSON)
                                                                              .body(chat, Chat.class)))
                .GET("/chats/chatUser/{userId}", accept(APPLICATION_JSON), request -> {
                    Sort sort = Sort.by(Sort.Direction.ASC, "lastChange");
                    return ok().contentType(APPLICATION_JSON).body(
                            repository.findAllByUsersId(request.pathVariable("userId"), sort), Chat.class);
                })
                .POST("/chats/{userId}/{interlocutorUserId}", chatHandler::findOrInsertChat)
                .DELETE("/chats/{id}",chatHandler::delete)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> composedMessageRouters(MessageRepository repository,
            ChatRepository chatRepository, ChatUserRepository chatUserRepository) {
        return route()
                .GET("/messages/{chatId}", accept(APPLICATION_JSON),
                        messageHandler::getMessageByChatId)
                .POST("messages/{chatId}/{chatUserId}/", messageHandler::insertMessage)
                .DELETE("/messages/{messageId}", messageHandler::deleteMessage)
                .build();
    }
}
