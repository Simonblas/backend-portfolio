package com.simon.backend_portfolio.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring Security
public class SecurityConfig {

    // 1. BEAN: Define el codificador de contraseñas (PasswordEncoder)
    // Spring inyectará este objeto BCryptPasswordEncoder en el UserService
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // se usa BCrypt, el estándar seguro
    }

    // 2. BEAN: Define las reglas de acceso (Autorización)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF para APIs REST que son stateless (sin sesiones de cookie)
                .csrf(csrf -> csrf.disable())

                // Configuración de CORS (necesario para la comunicación entre el frontend React y el backend)
                .cors(cors -> {
                })

                // Definición de reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Permite acceso PÚBLICO (sin autenticación) a todos los GET de la API
                        // Tu portafolio debe ser visible para todos.
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**").permitAll()

                        // Por ahora, permitiremos POST y PUT, pero *deben* ser protegidos después.
                        // Los protegeremos implementando JWT y Roles.
                        .requestMatchers("/api/**").permitAll()

                        // Cualquier otra solicitud requiere autenticación (por defecto)
                        .anyRequest().authenticated());

        // En pasos posteriores, aquí configuraremos el manejo de JWT y la gestión de sesiones.

        return http.build();
    }
}