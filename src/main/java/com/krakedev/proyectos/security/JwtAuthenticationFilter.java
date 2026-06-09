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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private TokenBlacklistService tokenBlacklistService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			try {
				if (tokenBlacklistService.esTokenInvalido(token)) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentType("text/plain;charset=UTF-8");
					response.getWriter().write("Token inválido: La sesión ha sido cerrada previamente (Blacklisted).");
					return;
				}

				DecodedJWT decodedJWT = jwtUtil.validarToken(token);
				String username = decodedJWT.getSubject();
				String rol = decodedJWT.getClaim("rol").asString();

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							username, null, Collections.singletonList(authority));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("text/plain;charset=UTF-8");
				response.getWriter().write("Error de autenticación: Token inválido o expirado. " + e.getMessage());
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}