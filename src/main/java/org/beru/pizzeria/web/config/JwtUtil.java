package org.beru.pizzeria.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
	private static final String SECRET_KEY = "Giobero140206";
	private static Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
	public String create(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuer("pizzeria-api")
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(15)))
				.sign(algorithm);
	}
	public boolean isValid(String jwt){
		try{
			JWT.require(algorithm)
					.build()
					.verify(jwt);
			return true;
		}catch (JWTVerificationException ex){
			return false;
		}
	}
	public String getUsername(String jwt){
		return JWT.require(algorithm)
				.build()
				.verify(jwt)
				.getSubject();
	}
}
