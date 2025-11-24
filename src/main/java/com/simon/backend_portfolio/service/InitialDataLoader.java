package com.simon.backend_portfolio.service;

import com.simon.backend_portfolio.model.User;
import com.simon.backend_portfolio.model.Skill; // Importar Skill
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final UserService userService;
    private final SkillService skillService;

    public InitialDataLoader(UserService userService, SkillService skillService) {
        this.userService = userService;
        this.skillService = skillService;
    }

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        // Verifica si ya existe al menos un usuario (para evitar duplicados al reiniciar)
        if (userService.getAllUsers().isEmpty()) {

            // --- 1. CREACIÓN DEL OBJETO USER ---
            User adminUser = new User();

            // --- 2. CREDENCIALES DE ACCESO (Necesario para Spring Security) ---
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(adminPassword); // Será hasheada por saveUser()
            adminUser.setRole("ROLE_ADMIN"); // Rol definido en la entidad

            // --- 3. TUS DATOS PERSONALES (Aquí usas los setters) ---
            adminUser.setNombre("Simón");
            adminUser.setApellido("Blas");
            adminUser.setTitulo("Full Stack Developer | Java & React");

            // Descripción larga de "Sobre Mí"
            adminUser.setSobreMi(
                    "Programador Full Stack con un año de experiencia en desarrollo web.\n" +
                            "Poseo bases sólidas en frontend y backend, aplicando buenas prácticas y enfocándome en la calidad del código.\n" +
                            "Soy responsable, objetivo y en permanente aprendizaje de nuevas tecnologías.\n" +
                            "Busco desarrollar soluciones eficientes, escalables y orientadas al usuario.\n" +
                            "Manejo tecnologías frontend como HTML, CSS, JavaScript y React, y backend con Java, Spring Boot, APIs REST, MySQL y JPA/Hibernate.\n" +
                            "En mis proyectos aplico arquitecturas modulares, gestión de versiones con Git/GitHub y principios de Clean Code.\n" +
                            "Actualmente estudio la Tecnicatura Universitaria en Programación a Distancia (UTN – Argentina), enfocándome en fundamentos sólidos de ingeniería de software y desarrollo backend."
            );


            adminUser.setEmailContacto("simoblas08@gmail.com");
            adminUser.setUbicacion("Azul, Buenos Aires, Argentina");
            adminUser.setFotoUrl("https://res.cloudinary.com/djiwka8mn/image/upload/w_300,h_300,c_fill,q_auto,f_auto/v1763988758/imagen_cv_owavyo.jpg");

            // --- 4. REDES SOCIALES ---
            adminUser.setGithubUrl("https://github.com/Simonblas");
            adminUser.setLinkedinUrl("https://www.linkedin.com/in/simonblas/");
            adminUser.setCurriculumPdfUrl("https://drive.google.com/uc?export=download&id=1XmfWIIeVrvtjQ5LvPlZusK4cGf9qLaBf");

            // --- 5. GUARDAR EN LA BASE DE DATOS ---
            userService.saveUser(adminUser); // El servicio hashea la contraseña y guarda

            System.out.println("Usuario administrador inicial creado: " + adminUser.getUsername());
        }
        // --- 2. CARGAR SKILLS INICIALES (Para Pruebas M-a-M) ---
        if (skillService.getAllSkills().isEmpty()) {

            // Skill 1: Backend
            skillService.saveSkill(new Skill("Spring Boot", "Intermedio", "Backend", "url_icono_spring"));

            // Skill 2: Frontend
            skillService.saveSkill(new Skill("React", "Avanzado", "Frontend", "url_icono_react"));

            // Skill 3: Base de Datos
            skillService.saveSkill(new Skill("MySQL", "Intermedio", "Database", "url_icono_mysql"));

            System.out.println("Skills iniciales cargadas.");
        }
    }
}