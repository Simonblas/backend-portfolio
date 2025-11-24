package com.simon.backend_portfolio.repository;


import com.simon.backend_portfolio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método clave que Spring Security usará para encontrar al usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
}