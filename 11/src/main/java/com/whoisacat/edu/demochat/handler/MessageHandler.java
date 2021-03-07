package com.whoisacat.edu.demochat.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface MessageHandler {

    Mono<ServerResponse> deleteMessage(ServerRequest request);

    Mono<ServerResponse> getMessageByChatId(ServerRequest request);

    Mono<ServerResponse> insertMessage(ServerRequest request);
}
