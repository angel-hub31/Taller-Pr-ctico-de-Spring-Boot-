package com.krakedev.proyectos.services;

import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();

    public void invalidarToken(String token) {
        if (token != null && !token.isBlank()) {
            blacklist.add(token.trim());
        }
    }

    
    public boolean esTokenInvalido(String token) {
        if (token == null || token.isBlank()) {
            return true;
        }
        return blacklist.contains(token.trim());
    }
}