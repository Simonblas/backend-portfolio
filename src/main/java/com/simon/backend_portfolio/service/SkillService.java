package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.Skill;
import com.simon.backend_portfolio.repository.SkillRepository;
import com.simon.backend_portfolio.exception.ResourceNotFoundException;
import com.simon.backend_portfolio.exception.DuplicateEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad no encontrada con ID: " + id));
    }

    public Skill saveSkill(Skill skill) {
        // Lógica de Unicidad: Comprueba si es un nuevo registro
        if (skill.getId() == null || skill.getId() == 0) {
            skillRepository.findByNombre(skill.getNombre()).ifPresent(s -> {
                throw new DuplicateEntryException("La habilidad '" + s.getNombre() + "' ya existe.");
            });
        }

        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
