package com.simon.backend_portfolio.service;


import com.simon.backend_portfolio.model.User;
import com.simon.backend_portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Método principal que Spring Security llama durante el login y la validación del token
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usa UserRepository para buscar el usuario
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convierte tu entidad User al formato UserDetails de Spring Security
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), // Contraseña hasheada
                // Asigna el rol (ej: ROLE_ADMIN) como una autoridad
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}