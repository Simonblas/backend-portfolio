package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.User;
import com.simon.backend_portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar la dependencia de seguridad
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // CRUCIAL para seguridad

    @Autowired
    // 1. Inyección de Dependencias: Ahora inyectamos el Repositorio Y el Codificador
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- LECTURA (Pública para el Frontend) ---

    // Obtener todos los usuarios (solo 1 admin)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Obtener el usuario por username (Método clave para Spring Security)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // --- ESCRITURA (Requiere Rol Admin) ---

// Archivo: src/main/java/com/simon/backend_portfolio.service/UserService.java

// ...

    // 2. Guardar/Actualizar la información del perfil
    public User saveUser(User user) {

        // --- 1. Lógica para Hashear la Contraseña ---
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Si el DTO de entrada tiene una contraseña, la hasheamos.
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else if (user.getId() != null) {
            // --- 2. Lógica para MANTENER la Contraseña Antigua (Actualización) ---

            // Si es una actualización (tiene ID) pero la contraseña está vacía,
            // cargamos el hash existente de la base de datos y lo reasignamos.
            Optional<User> existingUserOpt = userRepository.findById(user.getId());

            if (existingUserOpt.isPresent()) {
                // Sobreescribimos la contraseña vacía con la contraseña hasheada (segura)
                // que ya está en la BBDD.
                user.setPassword(existingUserOpt.get().getPassword());
            } else {
                // Lógica de error si el ID existe pero el usuario no. (Opcional)
                throw new RuntimeException("El usuario a actualizar no existe.");
            }
        }

        return userRepository.save(user);
    }

}