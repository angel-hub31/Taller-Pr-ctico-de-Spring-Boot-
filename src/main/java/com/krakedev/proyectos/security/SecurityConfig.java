package com.krakedev.proyectos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring
@EnableMethodSecurity // Permite usar @PreAuthorize en tus controladores
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar CSRF: Es necesario porque tu API es stateless y usa JWT, 
            // no sesiones basadas en cookies (CSRF es para formularios web tradicionales).
            .csrf(csrf -> csrf.disable())

            // 2. Política de sesión: Stateless significa que el servidor no guardará 
            // la sesión del usuario. Cada petición debe venir con su propio token.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 3. Autorización de rutas:
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/registrar", "/api/auth/login").permitAll() // Públicas
                .anyRequest().authenticated() // Todo lo demás requiere autenticación
            );

        // 4. Agregar filtro JWT: Indica a Spring que ejecute tu filtro personalizado 
        // ANTES de verificar el nombre de usuario y contraseña estándar.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 5. Codificador de contraseñas: Obligatorio para guardar claves cifradas en la BD
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}