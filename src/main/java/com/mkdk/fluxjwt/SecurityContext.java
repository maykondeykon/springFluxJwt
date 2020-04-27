package com.mkdk.fluxjwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContext implements ServerSecurityContextRepository {

	private final static String HEADER = "Bearer ";
	private AuthManager authManager;

	public SecurityContext(AuthManager authManager) {this.authManager = authManager;}

	@Override
	public Mono<Void> save(ServerWebExchange exchange,
	                       org.springframework.security.core.context.SecurityContext context) {
		return null;
	}

	@Override
	public Mono<org.springframework.security.core.context.SecurityContext> load(ServerWebExchange exchange) {
		ServerHttpRequest req = exchange.getRequest();
		String authHeader = req.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		String token = null;

		if (authHeader != null && authHeader.startsWith(HEADER)) {
			token = authHeader.replace(HEADER, "");
		}

		if (token != null) {
			Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
			return authManager.authenticate(auth).map(SecurityContextImpl::new);
		}

		return Mono.empty();
	}
}
