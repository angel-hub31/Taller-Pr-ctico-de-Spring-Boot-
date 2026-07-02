package com.krakedev.proyectos.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta: Debe ser robusta y secreta (en producción, cárgala desde variables de entorno)
    private static final String SECRET_KEY = "Krakedev_Clave_Secreta_Para_Firma_JWT_2026_Secure";

    // Tiempo de expiración: 7,200,000 ms = 2 horas
    private static final long EXPIRATION_TIME = 7200000;

    // Crea un JWT firmado con los datos del usuario
    public String generarToken(String username, String rol) {
        return JWT.create()
                .withSubject(username)       // Quién es el usuario
                .withClaim("rol", rol)       // Información extra (el rol)
                .withIssuedAt(new Date())    // Fecha de emisión
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de caducidad
                .sign(Algorithm.HMAC256(SECRET_KEY)); // Firma el token usando la clave secreta
    }

    // Valida que el token sea auténtico y no haya sido alterado
    public DecodedJWT validarToken(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verificador = JWT.require(algoritmo).build();
        return verificador.verify(token); // Lanza excepción si el token es inválido o expiró
    }
    
    // Métodos utilitarios para extraer datos del token ya validado
    public String obtenerUsername(String token) {
        return validarToken(token).getSubject();
    }

    public String obtenerRol(String token) {
        return validarToken(token).getClaim("rol").asString();
    }
}