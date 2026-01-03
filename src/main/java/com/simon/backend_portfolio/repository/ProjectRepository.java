package com.simon.backend_portfolio.repository;

import com.simon.backend_portfolio.model.Project;
import com.simon.backend_portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Buscar un proyecto por su título
    Optional<Project> findByTitulo(String titulo);

    /**
     * Busca todos los proyectos que contienen una habilidad específica.
     * Spring Data JPA resuelve automáticamente la consulta sobre la tabla intermedia.
     */
    List<Project> findBySkillsContaining(Skill skill);
}