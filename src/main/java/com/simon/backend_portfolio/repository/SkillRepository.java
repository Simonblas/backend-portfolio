package com.simon.backend_portfolio.repository;


import com.simon.backend_portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    // Opcional: Buscar una habilidad por nombre (útil para la lógica de negocio)
    Optional<Skill> findByNombre(String nombre);
}