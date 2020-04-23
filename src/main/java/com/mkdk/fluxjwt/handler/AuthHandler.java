package com.mkdk.fluxjwt.handler;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class AuthHandler {
	public Mono<ServerResponse> signUp(ServerRequest req){
		return ServerResponse.ok().body(BodyInserters.fromValue("oi"));
	}
}
