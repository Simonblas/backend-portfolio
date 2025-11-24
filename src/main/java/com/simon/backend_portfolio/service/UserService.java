package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.User;
import com.simon.backend_portfolio.repository.UserRepository;
import com.simon.backend_portfolio.exception.ResourceNotFoundException;
import com.simon.backend_portfolio.exception.DuplicateEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar la dependencia de seguridad
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    // 1. Inyección de Dependencias: Ahora inyectamos el Repositorio Y el Codificador
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- LECTURA (Pública para el Frontend) ---
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de usuario no encontrado con ID: " + id));
    }

    // Obtener el usuario por username (Método clave para Spring Security)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // --- ESCRITURA (Requiere Rol Admin) ---
    // Guardar/Actualizar la información del perfil
    public User saveUser(User user) {

        //Integridad de Datos: Validación básica de campos obligatorios
        if (user.getNombre() == null || user.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es un campo obligatorio.");
        }

        //Unicidad (solo si se está creando o si se actualiza el username/email)
        if (user.getId() == null) {
            userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
                throw new DuplicateEntryException("El nombre de usuario '" + u.getUsername() + "' ya existe.");
            });
        }

        //Lógica de Hasheo y Mantenimiento de Contraseña
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Hashear si se proporciona una nueva contraseña
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        } else if (user.getId() != null) {
            // Mantiene el hash de la contraseña antigua si no se proporcionó una nueva
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar, el perfil no existe."));

            user.setPassword(existingUser.getPassword());
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_ADMIN");
        }

        return userRepository.save(user);
    }

    // Método de eliminación (Opcional, ya que solo tendre un usuario admin)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}