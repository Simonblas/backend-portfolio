package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.Project;
import com.simon.backend_portfolio.model.Skill;
import com.simon.backend_portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SkillService skillService; // <-- Dependencia clave para M-a-M

    @Autowired
    public ProjectService(ProjectRepository projectRepository, SkillService skillService) {
        this.projectRepository = projectRepository;
        this.skillService = skillService;
    }

    // LECTURA
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    // ESCRITURA (CRUD Básico)
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // LÓGICA DE RELACIÓN (M-a-M)
    public Optional<Project> addSkillsToProject(Long projectId, Set<Long> skillIds) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) {
            return Optional.empty();
        }

        Project project = projectOpt.get();

        for (Long skillId : skillIds) {
            skillService.getSkillById(skillId)
                    .ifPresent(project::addSkill); // Añade al Set<Skill> del proyecto
        }

        return Optional.of(projectRepository.save(project)); // Guarda la relación
    }
}