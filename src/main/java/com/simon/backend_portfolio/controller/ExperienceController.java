package com.simon.backend_portfolio.controller;


import com.simon.backend_portfolio.model.Experience;
import com.simon.backend_portfolio.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
//@CrossOrigin(origins = "http://localhost:3000") asignamos esto en el cors global (SecurityConfig.java)
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    // GET: PÚBLICO
    @GetMapping
    public ResponseEntity<List<Experience>> getAllExperiences() {
        return new ResponseEntity<>(experienceService.getAllExperiences(), HttpStatus.OK);
    }

    // POST: ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
        return new ResponseEntity<>(experienceService.saveExperience(experience), HttpStatus.CREATED);
    }

    // PUT: ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @RequestBody Experience experience) {
        experience.setId(id); // Asegurar ID para la actualización
        return new ResponseEntity<>(experienceService.saveExperience(experience), HttpStatus.OK);
    }

    // DELETE: ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}