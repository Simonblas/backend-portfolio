package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.Project;
import com.simon.backend_portfolio.model.Skill;
import com.simon.backend_portfolio.repository.ProjectRepository;
import com.simon.backend_portfolio.exception.ResourceNotFoundException;
import com.simon.backend_portfolio.exception.DuplicateEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SkillService skillService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, SkillService skillService) {
        this.projectRepository = projectRepository;
        this.skillService = skillService;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));
    }

    public Project saveProject(Project project) {
        // Evitar duplicados por título
        if (project.getId() == null || project.getId() == 0) {
            projectRepository.findByTitulo(project.getTitulo()).ifPresent(p -> {
                throw new DuplicateEntryException("Ya existe un proyecto con el título: " + p.getTitulo());
            });
        }

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // --- LÓGICA DE RELACIÓN (M-a-M) ---
    public Project addSkillsToProject(Long projectId, Set<Long> skillIds) {

        // Lanza 404 si el proyecto no existe
        Project project = getProjectById(projectId);

        // Iterar sobre los IDs de skills
        for (Long skillId : skillIds) {
            try {
                // Si la skill no existe, lanza 404, pero el bucle continúa con las demás skills
                Skill skill = skillService.getSkillById(skillId);
                project.addSkill(skill);
            } catch (ResourceNotFoundException e) {
                // Esto permite asociar las skills válidas e ignorar las inválidas.
                System.out.println("Advertencia: Skill con ID " + skillId + " no encontrada. Se ignoró.");
            }
        }

        return projectRepository.save(project);
    }
}