package com.simon.backend_portfolio.controller;


import com.simon.backend_portfolio.model.User;
import com.simon.backend_portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // GET: /api/user (PÚBLICO) - Obtener perfil para la sección "Acerca de Mí"
    @GetMapping
    public ResponseEntity<User> getProfile() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Devuelve el primer usuario que encuentra (admin)
        return new ResponseEntity<>(users.get(0), HttpStatus.OK);
    }

    // PUT: /api/user (ADMIN) - Actualizar perfil
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')") // Requiere Token Válido con Rol ADMIN
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        // Asegura el ID del administrador actual para que el servicio lo actualice
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(users.get(0).getId());

        User updatedUser = userService.saveUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}