package com.krakedev.proyectos.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.krakedev.proyectos.entidades.Usuario;

// Repositorio para la entidad Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Consulta derivada: Spring Data JPA interpreta automáticamente el nombre del método.
     * Al escribir 'findByUsername', Spring genera la consulta: 
     * SELECT * FROM usuario WHERE username = ?
     * * Optional: Es una buena práctica para manejar casos donde el usuario 
     * podría no existir, evitando errores de tipo NullPointerException.
     */
    Optional<Usuario> findByUsername(String username);
}