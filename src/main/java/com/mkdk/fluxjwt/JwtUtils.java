package com.mkdk.fluxjwt;

import com.mkdk.fluxjwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {
	private static final long serialVersionUID = -2546470897975941814L;

	private static final String SECRET = "MY_SECRET";

	public String getUsername(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	public String genToken(User user){
		return Jwts.builder()
				.setSubject(user.getUsername())
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}
}
