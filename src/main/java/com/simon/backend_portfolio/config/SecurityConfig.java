package com.simon.backend_portfolio.config;

import com.simon.backend_portfolio.security.jwt.JwtEntryPoint;
import com.simon.backend_portfolio.security.jwt.JwtTokenFilter;
import com.simon.backend_portfolio.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize para asegurar métodos
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // URLs que serán públicas, incluyendo el endpoint de login
    private static final String[] WHITE_LIST_URLS = {"/api/auth/**",};

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt, el estándar para hashear contraseñas
        return new BCryptPasswordEncoder();
    }

    //Provedor de Autenticación (conecta el UserDetailsService y el PasswordEncoder)
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Nuestro servicio de usuario
        authProvider.setPasswordEncoder(passwordEncoder()); // Nuestro codificador
        return authProvider;
    }

    // Manager de Autenticación (el que orquesta el login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    //Cadena de Filtros de Seguridad (La configuración principal)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (es necesario en API REST sin sesiones)
                .csrf(csrf -> csrf.disable())

                // Configurar el manejo de excepciones de autenticación
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))

                // Configurar la gestión de sesiones como STATELESS (sin cookies de sesión)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Reglas de Autorización de Endpoints
                .authorizeHttpRequests(auth -> auth
                        //PERMITIR ACCESO PÚBLICO (GET) a todo el portafolio (Ej: /api/user, /api/skills)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**").permitAll()

                        // Permitir acceso a todas las URLs públicas sin autenticación
                        .requestMatchers(WHITE_LIST_URLS).permitAll()

                        // Asegurar las URLs de gestión de portfolio (solo ADMIN)
                        // Usamos un patrón más específico para evitar errores de matching
                        .requestMatchers("/api/projects/**", "/api/education/**", "/api/experience/**", "/api/skills/**").hasRole("ADMIN")

                        // Requerir autenticación para cualquier otra petición que no coincida
                        .anyRequest().authenticated());

        // Añadir nuestro filtro JWT ANTES del filtro estándar de autenticación de usuario y contraseña
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // Agregar el proveedor de autenticación
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}