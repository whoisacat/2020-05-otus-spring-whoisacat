package com.whoisacat.edu.demochat.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ChatUserHandler {

    Mono<ServerResponse> findOrInsert(ServerRequest request);

    Mono<ServerResponse> delete(ServerRequest request);
}
