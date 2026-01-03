package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.Skill;
import com.simon.backend_portfolio.model.Project;
import com.simon.backend_portfolio.repository.SkillRepository;
import com.simon.backend_portfolio.repository.ProjectRepository;
import com.simon.backend_portfolio.exception.ResourceNotFoundException;
import com.simon.backend_portfolio.exception.DuplicateEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository, ProjectRepository projectRepository) {
        this.skillRepository = skillRepository;
        this.projectRepository = projectRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad no encontrada con ID: " + id));
    }

    public Skill saveSkill(Skill skill) {
        // Validación de duplicados por nombre si es un registro nuevo
        if (skill.getId() == null || skill.getId() == 0) {
            skillRepository.findByNombre(skill.getNombre()).ifPresent(s -> {
                throw new DuplicateEntryException("La habilidad '" + s.getNombre() + "' ya existe.");
            });
        }
        return skillRepository.save(skill);
    }

    /**
     * Elimina una habilidad de forma segura.
     * Primero rompe los vínculos en la tabla intermedia 'project_skill'.
     */
    @Transactional
    public void deleteSkill(Long id) {
        // 1. Buscamos la habilidad para asegurar que existe
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede borrar, ID no encontrado: " + id));

        // 2. Buscamos todos los proyectos que están asociados a esta habilidad
        List<Project> projectsWithSkill = projectRepository.findBySkillsContaining(skill);

        // 3. Eliminamos la referencia de la habilidad en cada proyecto
        // Al removerla de la lista de 'skills' del proyecto, Hibernate genera
        // automáticamente el DELETE en la tabla de unión 'project_skill'
        for (Project project : projectsWithSkill) {
            project.getSkills().remove(skill);
            // No es necesario llamar a projectRepository.save() porque estamos en una @Transactional
        }

        // 4. Una vez limpia la relación, borramos la habilidad definitivamente
        skillRepository.delete(skill);
    }
}