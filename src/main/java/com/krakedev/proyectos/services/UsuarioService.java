package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * @Service: Gestiona la lógica de negocio para usuarios, 
 * incluyendo registro, búsqueda y autenticación.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registra un nuevo usuario cifrando su contraseña
    public Usuario registrar(Usuario usuario) {
        // Genera un "salt" aleatorio y cifra la contraseña
        String passwordHasheada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        usuario.setPassword(passwordHasheada);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Proceso de autenticación
    public Usuario login(String username, String password) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        
        // 1. Verificar si el usuario existe
        if (usuarioOpt.isEmpty()) {
            throw new Exception("Credenciales incorrectas (Usuario no encontrado)");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // 2. Comparar contraseña ingresada contra el hash almacenado
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            throw new Exception("Credenciales incorrectas (Contraseña inválida)");
        }
        
        return usuario;
    }

    // Obtiene un usuario específico o lanza excepción si no existe
    public Usuario obtenerPerfilUsuario(String username) throws Exception {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado: " + username));
    }
}