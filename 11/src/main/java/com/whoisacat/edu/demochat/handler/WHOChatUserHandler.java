package com.whoisacat.edu.demochat.handler;

import com.whoisacat.edu.demochat.domain.ChatUser;
import com.whoisacat.edu.demochat.repository.ChatUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class WHOChatUserHandler implements ChatUserHandler {

    private final ChatUserRepository repository;

    public WHOChatUserHandler(ChatUserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> findOrInsert(ServerRequest request) {
        //todo valid email filter
        ChatUser chatUser = new ChatUser();
        request.queryParam("email").ifPresent(email -> chatUser.setEmail(email.toLowerCase()));
        request.queryParam("title").ifPresent(chatUser::setTitle);
        return repository
                .existsByEmail(chatUser.getEmail())
                .flatMap(alreadyExists -> {
                    System.out.println(alreadyExists);
                    if (alreadyExists) {
                        return repository.findByEmail(chatUser.getEmail());
                    } else {
                        return repository.insert(chatUser);
                    }
                })
                .flatMap(persisted -> ok().contentType(APPLICATION_JSON).bodyValue(persisted));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String messageId = request.pathVariable("id");
        repository.deleteById(messageId).subscribe();
        return ServerResponse.ok().build();
    }
}
