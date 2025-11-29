package com.simon.backend_portfolio.controller;


import com.simon.backend_portfolio.dto.ProjectSkillRequest;
import com.simon.backend_portfolio.model.Project;
import com.simon.backend_portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
//@CrossOrigin(origins = "http://localhost:3000") asignamos esto en el cors global (SecurityConfig.java)
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // GET: PÚBLICO
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    // POST: ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return new ResponseEntity<>(projectService.saveProject(project), HttpStatus.CREATED);
    }

    // PUT: ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        projectDetails.setId(id);
        Project updatedProject = projectService.saveProject(projectDetails);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    // DELETE: ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // POST: /api/projects/add-skills (Relación M-a-M)
    @PostMapping("/add-skills")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> addSkillsToProject(@RequestBody ProjectSkillRequest request) {

        Project updatedProject = projectService.addSkillsToProject(request.getProjectId(), request.getSkillIds());
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }
}