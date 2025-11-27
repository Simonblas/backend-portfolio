package com.simon.backend_portfolio.controller;


import com.simon.backend_portfolio.model.Education;
import com.simon.backend_portfolio.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = "http://localhost:3000")
public class EducationController {

    @Autowired
    private EducationService educationService;

    // GET: PÚBLICO
    @GetMapping
    public ResponseEntity<List<Education>> getAllEducation() {
        return new ResponseEntity<>(educationService.getAllEducation(), HttpStatus.OK);
    }

    // POST: ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Education> createEducation(@RequestBody Education education) {
        return new ResponseEntity<>(educationService.saveEducation(education), HttpStatus.CREATED);
    }

    // PUT: ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Education> updateEducation(@PathVariable Long id, @RequestBody Education education) {
        education.setId(id);
        return new ResponseEntity<>(educationService.saveEducation(education), HttpStatus.OK);
    }

    // DELETE: ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}