# Backend Portfolio API (Java - Spring)

### Este repositorio contiene la implementación del servicio backend para una aplicación de portafolio personal.

El sistema está construido utilizando Java con Spring Boot y sigue una arquitectura RESTful. Su objetivo principal es
exponer
endpoints para la gestión de proyectos, habilidades, experiencia laboral y educación, asegurando la integridad de los
datos mediante autenticación y autorización basada en tokens (JWT).

---

## Descripción Técnica

El sistema está diseñado bajo una
arquitectura en capas (Modelo-Repositorio-Servicio-Controlador) y opera de manera "stateless" (sin estado), delegando la
persistencia de la sesión al cliente mediante JSON Web Tokens.

---

## Tecnologías Utilizadas:

#### Lenguaje: Java 21 (JDK 21)

#### Framework Principal: Spring Boot 3.5.7

#### Seguridad: Spring Security 6 + JWT (jjwt 0.12.5)

#### Base de Datos: MySQL

#### ORM: Spring Data JPA / Hibernate

#### Gestión de Dependencias: Apache Maven

#### Utilidades: Lombok

---

## Arquitectura de Seguridad

La seguridad es el componente central de esta aplicación. Se implementa un esquema de autenticación y autorización
robusto con las siguientes características:

### Cifrado de Contraseñas:

Se utiliza BCryptPasswordEncoder para el hasheo de credenciales antes de su persistencia en la base de datos.

### Autenticación JWT:

Implementación personalizada de JwtProvider y JwtTokenFilter para la generación y validación de tokens firmados con
algoritmo HS512.

### Control de Acceso Basado en Roles (RBAC):

#### Acceso Público: Lectura de datos (GET) y endpoint de autenticación.

#### Acceso Administrador (ROLE_ADMIN): Escritura, modificación y eliminación de recursos (POST, PUT, DELETE).

### Manejo de Errores:

Implementación de JwtEntryPoint para gestionar intentos de acceso no autorizados (401) y GlobalControllerAdvice para
excepciones de negocio.

---

## Instalación y Configuración

### Prerrequisitos:

#### - Java Development Kit (JDK) 21 instalado.

#### - Maven configurado en las variables de entorno.

#### - Servidor MySQL en ejecución.

---

### Configuración de Credenciales

#### Por motivos de seguridad, las credenciales sensibles no se incluyen en el repositorio. Debe crear manualmente el archivo de configuración de datos secretos.

#### - Navegue a ```src/main/resources/```

#### - Cree un archivo llamado ```application-secrets.properties```

#### - Agregue el siguiente contenido reemplazando los valores con su configuración local:

```
// Credenciales de Base de Datos:
spring.datasource.username=root
spring.datasource.password=SU_PASSWORD_MYSQL

// Credenciales del Administrador Inicial (Se crearán en el primer arranque)
admin.username=su_admin_usuario
admin.password=su_admin_password_segura

// Configuración JWT (Clave Base64 de 512 bits mínimo)
jwt.secret=SU_CLAVE_SECRETA_BASE64_MUY_LARGA_
jwt.expiration=(valor numerico aqui)
```

---

## Ejecución del Proyecto

### Clone el repositorio:

```git clone <URL_DEL_REPOSITORIO>```

### Instale las dependencias y compile el proyecto:

``` mvn clean install -DskipTests```

### Ejecute la aplicación:

```mvn spring-boot:run```

Al iniciar la aplicación por primera vez, la clase InitialDataLoader verificará si la base de datos está vacía. Si es
así, creará automáticamente el usuario administrador definido en el archivo de datos secretos.

---

## Documentación de la API

La API expone los siguientes recursos. Todas las respuestas son en formato
JSON.

### Autenticación

| Método   | Endpoint          | Descripción                                  | Acceso  |
|:---------|:------------------|:---------------------------------------------|:--------|
| **POST** | `/api/auth/login` | Recibe credenciales y devuelve un token JWT. | Público |

---

### Proyectos (Projects)

| Método     | Endpoint                   | Descripción                                  | Acceso    |
|:-----------|:---------------------------|:---------------------------------------------|:----------|
| **GET**    | `/api/projects`            | Lista todos los proyectos.                   | Público   |
| **POST**   | `/api/projects`            | Crea un nuevo proyecto.                      | **Admin** |
| **PUT**    | `/api/projects/{id}`       | Actualiza un proyecto existente.             | **Admin** |
| **DELETE** | `/api/projects/{id}`       | Elimina un proyecto.                         | **Admin** |
| **POST**   | `/api/projects/add-skills` | Asocia habilidades existentes a un proyecto. | **Admin** |

---

### Habilidades (Skills)

| Método     | Endpoint           | Descripción                           | Acceso    |
|:-----------|:-------------------|:--------------------------------------|:----------|
| **GET**    | `/api/skills`      | Lista todas las habilidades técnicas. | Público   |
| **POST**   | `/api/skills`      | Crea una nueva habilidad.             | **Admin** |
| **DELETE** | `/api/skills/{id}` | Elimina una habilidad.                | **Admin** |

---

### Perfil de Usuario (User)

| Método  | Endpoint    | Descripción                                | Acceso    |
|:--------|:------------|:-------------------------------------------|:----------|
| **GET** | `/api/user` | Obtiene la información del perfil público. | Público   |
| **PUT** | `/api/user` | Actualiza la información del perfil.       | **Admin** |

---

### Experiencia y Educación

Los endpoints para `/api/experience` y `/api/education` siguen el mismo patrón CRUD que los anteriores:

* **GET (Lista/Detalle):** Público.
* **POST, PUT, DELETE:** Restringido a **Admin**.

---

### Códigos de Estado HTTP

El backend implementa un manejo de excepciones global para devolver códigos de estado precisos:

* **200 OK:** Petición exitosa.
* **201 Created:** Recurso creado exitosamente.
* **204 No Content:** Eliminación exitosa.
* **400 Bad Request:** Error de validación (ej. fechas inválidas o datos faltantes).
* **401 Unauthorized:** Token JWT faltante, inválido o expirado.
* **403 Forbidden:** Token válido pero privilegios insuficientes (Rol no es ADMIN).
* **404 Not Found:** El recurso solicitado no existe en la base de datos.
* **409 Conflict:** Intento de duplicar un registro único (ej. nombre de usuario o título de proyecto).

---

### Estructura del Proyecto

```text
src/main/java/com/simon/backend_portfolio
├── config/          # Configuración de Beans y Seguridad
├── controller/      # Endpoints de la API REST
├── dto/             # Objetos de Transferencia de Datos
├── exception/       # Excepciones personalizadas
├── model/           # Entidades JPA (Base de datos)
├── repository/      # Interfaces de acceso a datos (DAO)
├── security/        # Lógica JWT (Provider, Filter, EntryPoint)
└── service/         # Lógica de negocio