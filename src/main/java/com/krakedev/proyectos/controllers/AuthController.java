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
@RequestMapping("/api/auth") // Define la ruta base para todos los endpoints de autenticación

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.POST}, allowedHeaders = {"Authorization", "Content-Type"})
public class AuthController {

    // Inyección de dependencias para los servicios de lógica y seguridad
    @Autowired private UsuarioService usuarioService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private TokenBlacklistService tokenBlacklistService;

    // POST /api/auth/registrar: Crea un nuevo usuario
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

    // POST /api/auth/login: Valida credenciales y genera un JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            // Valida usuario y contraseña contra la DB
            Usuario usuarioAutenticado = usuarioService.login(usuario.getUsername(), usuario.getPassword());

            // Genera el token JWT si las credenciales son correctas
            String token = jwtUtil.generarToken(usuarioAutenticado.getUsername(), usuarioAutenticado.getRol());

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("username", usuarioAutenticado.getUsername());
            respuesta.put("rol", usuarioAutenticado.getRol());
            respuesta.put("token", token);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    // POST /api/auth/logout: Invalida el token actual (añadiéndolo a una lista negra)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extrae el token del header Authorization: Bearer <token>
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                tokenBlacklistService.invalidarToken(token);
                return ResponseEntity.ok("Sesión cerrada exitosamente. Token invalidado.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Formato de cabecera Authorization inválido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el cierre de sesión: " + e.getMessage());
        }
    }
}