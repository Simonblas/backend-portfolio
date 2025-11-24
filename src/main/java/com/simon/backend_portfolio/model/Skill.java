package com.simon.backend_portfolio.model;


import jakarta.persistence.*;

import java.util.Set; // Importado para la relación ManyToMany

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String nivel; // ej: Avanzado, Intermedio

    @Column(nullable = false)
    private String categoria; // ej: Backend, Frontend, Soft Skill

    private String iconoUrl;

    // Relación inversa ManyToMany con Project. Mapeado por 'skills' en la entidad Project
    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Project> projects;

    public Skill() {
    }

    // Constructor completo
    public Skill(String nombre, String nivel, String categoria, String iconoUrl) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.categoria = categoria;
        this.iconoUrl = iconoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
    }

    // ... otros getters y setters
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}