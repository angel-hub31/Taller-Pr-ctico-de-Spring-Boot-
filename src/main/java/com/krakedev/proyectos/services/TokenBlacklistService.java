package com.krakedev.proyectos.services;

import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Service: Indica que esta clase es un componente de Spring.
 * Este servicio gestiona una "lista negra" en memoria para invalidar tokens
 * antes de que alcancen su fecha de expiración natural.
 */
@Service
public class TokenBlacklistService {

    // Utilizamos ConcurrentHashMap.newKeySet() para que sea seguro en entornos 
    // multihilo (varias peticiones accediendo al mismo tiempo)
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    // Agrega el token a la lista negra (proceso de logout)
    public void invalidarToken(String token) {
        if (token != null && !token.isBlank()) {
            blacklist.add(token.trim());
        }
    }

    // Verifica si el token ha sido invalidado previamente
    public boolean esTokenInvalido(String token) {
        if (token == null || token.isBlank()) {
            return true;
        }
        return blacklist.contains(token.trim());
    }
}