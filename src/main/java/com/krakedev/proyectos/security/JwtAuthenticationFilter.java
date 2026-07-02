package com.krakedev.proyectos.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.krakedev.proyectos.services.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

// Se ejecuta una vez por cada petición HTTP que llega al servidor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired private JwtUtil jwtUtil;
	@Autowired private TokenBlacklistService tokenBlacklistService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		// Verifica si la petición incluye un token Bearer
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7); // Quita el prefijo "Bearer "

			try {
				// 1. Verifica si el token está en la lista negra (logout previo)
				if (tokenBlacklistService.esTokenInvalido(token)) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().write("Token en lista negra.");
					return;
				}

				// 2. Valida el token y extrae información (usuario y rol)
				DecodedJWT decodedJWT = jwtUtil.validarToken(token);
				String username = decodedJWT.getSubject();
				String rol = decodedJWT.getClaim("rol").asString();

				// 3. Si es válido y no hay sesión activa, establece la autenticación en el contexto de Spring
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							username, null, Collections.singletonList(authority));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			} catch (Exception e) {
				// Si el token es inválido o expiró, bloquea la petición
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}

		// Continúa con el siguiente filtro en la cadena de seguridad de Spring
		filterChain.doFilter(request, response);
	}
}