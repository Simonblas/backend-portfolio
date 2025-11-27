package com.simon.backend_portfolio.model;

import jakarta.persistence.*;

@Entity
// Usamos un nombre de tabla explícito para evitar conflictos con palabras reservadas de SQL.
@Table(name = "user_profile")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- CAMPOS DE INFORMACIÓN PERSONAL ---
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    // Título profesional (ej: "Backend Developer")
    private String titulo;

    @Column(nullable = false, unique = true) // Asegura que no haya dos usuarios con el mismo email
    private String emailContacto;

    @Column(nullable = false, length = 2000) // Un campo más largo para la sección "Sobre Mí"
    private String sobreMi;

    private String fotoUrl;
    private String ubicacion;

    // --- CAMPOS DE REDES SOCIALES ---
    private String githubUrl;
    private String linkedinUrl;
    private String curriculumPdfUrl;

    // --- CAMPOS DE SEGURIDAD ---
    // Usaremos esta entidad para la autenticación
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Se guarda hasheada con Spring Security

    @Column(nullable = false)
    private String role = "ROLE_ADMIN"; // Define rol como administrador

    // Constructor vacío (para JPA/Hibernate)
    public User() {
    }

    public User(Long id, String nombre, String apellido, String titulo, String emailContacto, String sobreMi, String fotoUrl, String ubicacion, String githubUrl, String linkedinUrl, String curriculumPdfUrl, String username, String password, String role) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
        this.emailContacto = emailContacto;
        this.sobreMi = sobreMi;
        this.fotoUrl = fotoUrl;
        this.ubicacion = ubicacion;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.curriculumPdfUrl = curriculumPdfUrl;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getSobreMi() {
        return sobreMi;
    }

    public void setSobreMi(String sobreMi) {
        this.sobreMi = sobreMi;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getCurriculumPdfUrl() {
        return curriculumPdfUrl;
    }

    public void setCurriculumPdfUrl(String curriculumPdfUrl) {
        this.curriculumPdfUrl = curriculumPdfUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}