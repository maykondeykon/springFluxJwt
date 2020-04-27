package com.mkdk.fluxjwt.handler;


import com.mkdk.fluxjwt.JwtUtils;
import com.mkdk.fluxjwt.model.User;
import com.mkdk.fluxjwt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class AuthHandler {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private JwtUtils jwtUtils;

	public AuthHandler(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtils jwtUtils) {this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}

	public Mono<ServerResponse> signUp(ServerRequest req) {
		Mono<User> userMono = req.bodyToMono(User.class);
		return userMono.map(u -> {
			User userPass = new User(u.getUsername(), u.getPassword());
			userPass.setPassword(passwordEncoder.encode(u.getPassword()));
			return userPass;
		}).flatMap(userRepository::save)
				.flatMap(user -> ServerResponse.ok().body(BodyInserters.fromValue(user)));
	}

	public Mono<ServerResponse> login(ServerRequest req) {
		Mono<User> userMono = req.bodyToMono(User.class);
		return userMono
				.flatMap(u -> userRepository.findByUsername(u.getUsername())
				.flatMap(user -> {
					if (passwordEncoder.matches(u.getPassword(), user.getPassword())){
						return ServerResponse.ok()
								.body(BodyInserters.fromValue(jwtUtils.genToken(user)));
					}
					return ServerResponse.badRequest()
							.body(BodyInserters.fromValue("Invalid credentials"));
				})).switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue("User does not exists")));
	}
}
