package com.example.demo.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Este método se ejecuta en cada solicitud para verificar el token JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrae el token de la cabecera de autorización
        final String token = getTokenFromRequest(request);

        // Si no hay token, se permite que la solicitud continúe sin hacer ninguna verificación
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Aquí puedes agregar lógica adicional para validar el token (por ejemplo, decodificarlo)
        // Si el token es válido, podrías cargar detalles del usuario, configurar la seguridad, etc.

        // Continúa con el siguiente filtro en la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Este método extrae el token de la cabecera Authorization
    private String getTokenFromRequest(HttpServletRequest request) {
        // Obtiene la cabecera Authorization
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verifica que la cabecera tenga un valor válido y comience con "Bearer "
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            // Extrae el token eliminando la parte "Bearer "
            return authHeader.substring(7);
        }

        // Si no se encuentra un token válido, retorna null
        return null;
    }
}
