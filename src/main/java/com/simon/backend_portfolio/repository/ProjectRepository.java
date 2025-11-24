package com.simon.backend_portfolio.repository;


import com.simon.backend_portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Opcional: Buscar un proyecto por título
    Optional<Project> findByTitulo(String titulo);
}