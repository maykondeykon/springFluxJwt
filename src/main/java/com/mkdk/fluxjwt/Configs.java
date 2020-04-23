package com.mkdk.fluxjwt;

import com.mkdk.fluxjwt.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Configs {

	@Bean
	public RouterFunction<ServerResponse> auth(AuthHandler handler){
		return RouterFunctions.route(GET("/sign-up").and(accept(APPLICATION_JSON)), handler::signUp);
	}
}
