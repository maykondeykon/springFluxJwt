package com.mkdk.fluxjwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfigsFilter {

	private AuthManager authManager;
	private SecurityContext securityContext;

	public SecurityConfigsFilter(AuthManager authManager, SecurityContext securityContext) {
		this.authManager = authManager;
		this.securityContext = securityContext;
	}

	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
		return http.cors().disable()
				.csrf().disable()
				.authenticationManager(authManager)
				.securityContextRepository(securityContext)
				.authorizeExchange()
				.pathMatchers("/sign-up/**", "/login/**").permitAll()
				.pathMatchers(HttpMethod.OPTIONS).permitAll()
				.anyExchange().authenticated()
				.and()
				.build();

	}

	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
