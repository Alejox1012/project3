package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtros y autenticación personalizados
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Deshabilitar CSRF ya que estamos usando autenticación basada en tokens (stateless)
            .csrf(csrf -> csrf.disable())
            
            // Configuración de las rutas de autorización
            .authorizeHttpRequests(auth -> 
                auth
                    .requestMatchers("/auth/**").permitAll()  // Rutas de autenticación no requieren autenticación
                    .anyRequest().authenticated()  // Resto de las rutas requieren autenticación
            )
            
            // Configuración para que no se mantenga el estado de sesión (stateless)
            .sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Establecer el AuthenticationProvider
            .authenticationProvider(authProvider)
            
            // Agregar el filtro JWT antes de la autenticación por nombre y contraseña
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            
            // Construir la configuración de seguridad
            .build();
    }
}
