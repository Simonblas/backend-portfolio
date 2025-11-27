package com.simon.backend_portfolio.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    /**
     * Este método se llama cuando un usuario no autenticado (sin token válido)
     * intenta acceder a un recurso que requiere autenticación.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        // Devolver el código de estado 401 Unauthorized y un mensaje de error claro.
        // Esto es lo que verá el frontend de React si intenta acceder a un POST/PUT sin token.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso denegado: Token inválido o no proporcionado.");
    }
}