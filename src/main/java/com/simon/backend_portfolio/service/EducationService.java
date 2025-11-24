package com.simon.backend_portfolio.service;


import com.simon.backend_portfolio.model.Education;
import com.simon.backend_portfolio.repository.EducationRepository;
import com.simon.backend_portfolio.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getAllEducation() {
        Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "fechaInicio");
        return educationRepository.findAll(sortByDateDesc);
    }

    public Education getEducationById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de educación no encontrado con ID: " + id));
    }

    // ESCRITURA (Requiere Rol Admin)
    public Education saveEducation(Education education) {

        if (education.getFechaFin() != null && education.getFechaInicio().isAfter(education.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        return educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }
}