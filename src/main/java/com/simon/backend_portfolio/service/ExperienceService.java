package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.Experience;
import com.simon.backend_portfolio.repository.ExperienceRepository;
import com.simon.backend_portfolio.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    // LECTURA
    public List<Experience> getAllExperiences() {
        Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "fechaInicio");
        return experienceRepository.findAll(sortByDateDesc);
    }


    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro de experiencia no encontrado con ID: " + id));
    }

    public Experience saveExperience(Experience experience) {

        if (experience.getFechaFin() != null && experience.getFechaInicio().isAfter(experience.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        return experienceRepository.save(experience);
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}