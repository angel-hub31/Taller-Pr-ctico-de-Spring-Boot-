package com.krakedev.proyectos.controllers;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.security.JwtUtil;
import com.krakedev.proyectos.services.TokenBlacklistService;
import com.krakedev.proyectos.services.UsuarioService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private TokenBlacklistService tokenBlacklistService;

	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
		try {
			Usuario nuevoUsuario = usuarioService.registrar(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al registrar el usuario: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {
		try {
			Usuario usuarioAutenticado = usuarioService.login(usuario.getUsername(), usuario.getPassword());

			String token = jwtUtil.generarToken(usuarioAutenticado.getUsername(), usuarioAutenticado.getRol());

			Map<String, String> respuesta = new HashMap<>();
			respuesta.put("token", token);
			respuesta.put("username", usuarioAutenticado.getUsername());
			respuesta.put("rol", usuarioAutenticado.getRol());

			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
		try {
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);

				tokenBlacklistService.invalidarToken(token);

				return ResponseEntity.ok("Sesión cerrada exitosamente. Token invalidado.");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Formato de cabecera Authorization inválido (Debe iniciar con Bearer).");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al procesar el cierre de sesión: " + e.getMessage());
		}
	}

}