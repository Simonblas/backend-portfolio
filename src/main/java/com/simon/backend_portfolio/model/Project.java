package com.simon.backend_portfolio.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String descripcion;

    private String urlDemo;
    private String urlRepo;
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate fecha; // Fecha de finalización del proyecto

    // Relación Muchos-a-Muchos con Skill
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_skill", // Nombre de la tabla intermedia que crea Hibernate
            joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills = new HashSet<>();
    // Usamos un Set para asegurar que una skill no esté duplicada en un proyecto

    public Project() {
    }

    public Project(String titulo, String descripcion, String urlDemo, String urlRepo, String imageUrl, LocalDate fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.urlDemo = urlDemo;
        this.urlRepo = urlRepo;
        this.imageUrl = imageUrl;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlDemo() {
        return urlDemo;
    }

    public void setUrlDemo(String urlDemo) {
        this.urlDemo = urlDemo;
    }

    public String getUrlRepo() {
        return urlRepo;
    }

    public void setUrlRepo(String urlRepo) {
        this.urlRepo = urlRepo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // ... otros getters y setters
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }
}