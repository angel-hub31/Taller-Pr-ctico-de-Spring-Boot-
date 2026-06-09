package com.krakedev.proyectos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "Krakedev_Clave_Secreta_Para_Firma_JWT_2026_Secure";

	private static final long EXPIRATION_TIME = 7200000;

	public String generarToken(String username, String rol) {
		return JWT.create().withSubject(username).withClaim("rol", rol).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC256(SECRET_KEY));
	}

	public DecodedJWT validarToken(String token) {
		Algorithm algoritmo = Algorithm.HMAC256(SECRET_KEY);
		JWTVerifier verificador = JWT.require(algoritmo).build();
		return verificador.verify(token);
	}
}