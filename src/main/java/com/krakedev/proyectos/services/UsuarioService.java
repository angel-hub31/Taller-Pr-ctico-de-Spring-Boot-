package com.krakedev.proyectos.services;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrar(Usuario usuario) {
        String passwordHasheada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        usuario.setPassword(passwordHasheada);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
    public Usuario login(String username, String password) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            throw new Exception("Credenciales incorrectas (Usuario no encontrado)");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            throw new Exception("Credenciales incorrectas (Contraseña inválida)");
        }
        
        return usuario;
    }
    public Usuario obtenerPerfilUsuario(String username) throws Exception {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuario no encontrado con el username: " + username));
    }
    
}