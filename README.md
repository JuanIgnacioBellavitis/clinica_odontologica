# 🦷 Sistema de Gestión para Clínica Odontológica

Sistema de gestión completo desarrollado con **Spring Boot** para administrar pacientes, odontólogos, turnos y usuarios de una clínica odontológica. Incluye autenticación y autorización mediante Spring Security.

---

## 📋 Descripción

Este proyecto es una **aplicación web RESTful** construida con Spring Boot que implementa un sistema CRUD completo para la gestión integral de una clínica odontológica. Utiliza **Spring Data JPA** para la persistencia de datos con una base de datos **H2** en memoria, y **Spring Security** para la gestión de usuarios y autenticación.

### Características Principales

- ✅ **Gestión completa de Pacientes** (CRUD)
- ✅ **Gestión completa de Odontólogos** (CRUD)
- ✅ **Gestión de Turnos** (CRUD) con relaciones entre Pacientes y Odontólogos
- ✅ **Sistema de autenticación y autorización** con Spring Security
- ✅ **Roles de usuario** (ROLE_USER, ROLE_ADMIN)
- ✅ **Registro de usuarios** y login
- ✅ **API REST** completa
- ✅ **Interfaz web** con HTML/JavaScript
- ✅ **Manejo de excepciones** centralizado
- ✅ **DTOs** para transferencia de datos
- ✅ **Base de datos H2** con consola habilitada
- ✅ **Tests unitarios e integración**

---

## 🛠️ Stack Tecnológico

### Dependencias Principales

- **Java 21**
- **Spring Boot 3.5.6**
  - `spring-boot-starter-web`: Framework web y REST APIs
  - `spring-boot-starter-data-jpa`: Persistencia con JPA/Hibernate
  - `spring-boot-starter-security`: Autenticación y autorización
  - `spring-boot-devtools`: Herramientas de desarrollo
  - `spring-boot-starter-test`: Testing (JUnit, Mockito)
- **H2 Database**: Base de datos en memoria
- **Lombok**: Reducción de código boilerplate
- **Log4j 1.2.12**: Logging
- **Maven**: Gestión de dependencias

### Configuración de Base de Datos

```properties
spring.datasource.url=jdbc:h2:~/test
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```

**Acceso a H2 Console**: `http://localhost:8080/h2-console`

---

## 📁 Estructura del Proyecto

```
clinica_odontologica/
├── src/
│   ├── main/
│   │   ├── java/com/clinica_odontologica/clinica_odontologica/
│   │   │   ├── ClinicaOdontologicaApplication.java
│   │   │   ├── controller/              # Capa de controladores REST
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── OdontologoController.java
│   │   │   │   ├── PacienteController.java
│   │   │   │   └── TurnoController.java
│   │   │   ├── dto/                     # Data Transfer Objects
│   │   │   │   ├── ExceptionDTO.java
│   │   │   │   ├── OdontologoDTO.java
│   │   │   │   ├── PacienteDTO.java
│   │   │   │   ├── RegistroDTO.java
│   │   │   │   └── TurnoDTO.java
│   │   │   ├── entity/                  # Entidades JPA
│   │   │   │   ├── Domicilio.java
│   │   │   │   ├── Odontologo.java
│   │   │   │   ├── Paciente.java
│   │   │   │   ├── Turno.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── UsuarioRol.java (enum)
│   │   │   ├── exceptions/              # Manejo de excepciones
│   │   │   │   ├── BadRequestException.java
│   │   │   │   ├── ExceptionController.java
│   │   │   │   └── NotFoundException.java
│   │   │   ├── repository/              # Repositorios Spring Data JPA
│   │   │   │   ├── OdontologoRepository.java
│   │   │   │   ├── PacienteRepository.java
│   │   │   │   ├── TurnoRepository.java
│   │   │   │   └── UsuarioRepository.java
│   │   │   ├── security/                # Configuración de seguridad
│   │   │   │   ├── DatosIniciales.java
│   │   │   │   ├── PasswordEncoder.java
│   │   │   │   └── WebConfigSecurity.java
│   │   │   └── service/                 # Capa de servicios
│   │   │       ├── IOdontologoService.java
│   │   │       ├── IPacienteService.java
│   │   │       ├── ITurnoService.java
│   │   │       ├── OdontologoService.java
│   │   │       ├── PacienteService.java
│   │   │       ├── TurnoService.java
│   │   │       └── UsuarioService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/                  # Archivos estáticos (HTML, CSS, JS)
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   └── *.html
│   └── test/
│       └── java/com/clinica_odontologica/clinica_odontologica/
│           ├── ClinicaOdontologicaApplicationTests.java
│           ├── OdontologoTest.java
│           ├── PacienteTest.java
│           ├── TurnoTest.java
│           └── *IntegrationTest.java
└── pom.xml
```

---

## 🗄️ Modelo de Datos

### Entidades

#### 1. **Paciente**
- `id` (Long, PK, Auto-increment)
- `nombre` (String)
- `apellido` (String)
- `numeroContacto` (int)
- `fechaIngreso` (LocalDate)
- `email` (String, unique)
- `domicilio` (OneToOne con Domicilio)
- `turnos` (OneToMany con Turno, LAZY)

#### 2. **Odontologo**
- `id` (Long, PK, Auto-increment)
- `nombre` (String)
- `apellido` (String)
- `matricula` (Integer, unique, not null)
- `turnos` (OneToMany con Turno, LAZY)

#### 3. **Turno**
- `id` (Long, PK, Auto-increment)
- `fecha` (LocalDate)
- `paciente` (ManyToOne con Paciente)
- `odontologo` (ManyToOne con Odontologo)

#### 4. **Domicilio**
- `id` (Long, PK, Auto-increment)
- `calle` (String)
- `numero` (int)
- `localidad` (String)
- `provincia` (String)

#### 5. **Usuario**
Implementa `UserDetails` para Spring Security:
- `id` (Long, PK, Auto-increment)
- `nombre` (String)
- `apellido` (String)
- `userName` (String)
- `email` (String, unique, not null) - Usado como username para login
- `password` (String, codificado con BCrypt)
- `usuarioRol` (UsuarioRol enum)

#### 6. **UsuarioRol** (Enum)
- `ROLE_USER`
- `ROLE_ADMIN`

### Relaciones

```
Paciente (1) ──── (N) Turno (N) ──── (1) Odontologo
   │                                       
   └─── (1) Domicilio
```

---

## 🔐 Seguridad

### Configuración de Seguridad

El proyecto utiliza **Spring Security** con las siguientes características:

#### **WebConfigSecurity**
- **Autenticación**: Form-based login con página personalizada (`/login.html`)
- **Password Encoding**: BCryptPasswordEncoder
- **UserDetailsService**: `UsuarioService` carga usuarios desde la base de datos
- **CSRF**: Deshabilitado para facilitar el desarrollo

#### **Rutas Públicas** (sin autenticación):
- `/auth/register` - Registro de usuarios
- `/login.html` - Página de login
- `/register.html` - Página de registro
- `/pacienteLista.html`, `/odontologoLista.html`, `/turnoLista.html` - Listas públicas
- `/css/**`, `/js/**` - Recursos estáticos

#### **Rutas Protegidas** (requieren autenticación):
- `/auth/me` - Información del usuario actual
- Todas las rutas `/paciente/**`, `/odontologo/**`, `/turno/**` (endpoints REST)
- Todas las demás rutas

#### **Usuario Administrador por Defecto**

Al iniciar la aplicación, se crea automáticamente un usuario administrador:

```
Email: juan@mail.com
Password: admin
Rol: ROLE_ADMIN
```

Este usuario se crea mediante `DatosIniciales` que implementa `ApplicationRunner`.

---

## 📡 API REST - Endpoints

### Base URL: `http://localhost:8080`

### 🔓 Autenticación

#### **POST** `/auth/register`
Registra un nuevo usuario.

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "userName": "juanito",
  "email": "juan@example.com",
  "password": "password123"
}
```

**Response:** `200 OK`
```json
"Usuario registrado con éxito: juan@example.com"
```

#### **GET** `/auth/me`
Obtiene información del usuario autenticado.

**Headers:** `Authorization` requerido

**Response:** `200 OK`
```json
{
  "nombre": "Juan",
  "apellido": "Bellavitis",
  "email": "juan@mail.com",
  "rol": "ROLE_ADMIN"
}
```

---

### 👤 Pacientes

#### **GET** `/paciente`
Lista todos los pacientes.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nombre": "Homero",
    "apellido": "Simpson",
    "numeroContacto": 11223344,
    "fechaIngreso": "2024-01-15",
    "domicilio": {
      "id": 1,
      "calle": "Siempre Viva",
      "numero": 723,
      "localidad": "Springfield",
      "provincia": "USA"
    },
    "email": "homero@mail.com"
  }
]
```

#### **GET** `/paciente/{id}`
Busca un paciente por ID.

**Response:** `200 OK` o `404 NOT FOUND`

#### **POST** `/paciente/crear`
Crea un nuevo paciente.

**Request Body:**
```json
{
  "nombre": "Bart",
  "apellido": "Simpson",
  "numeroContacto": 12345678,
  "fechaIngreso": "2024-01-20",
  "email": "bart@mail.com",
  "domicilio": {
    "calle": "Siempre Viva",
    "numero": 723,
    "localidad": "Springfield",
    "provincia": "USA"
  }
}
```

**Response:** `200 OK` con el paciente creado o `400 BAD REQUEST` si el email ya existe

#### **PUT** `/paciente/modificar/{id}`
Modifica un paciente existente.

**Request Body:** Mismo formato que crear

**Response:** `200 OK` o `404 NOT FOUND`

#### **DELETE** `/paciente/eliminar/{id}`
Elimina un paciente.

**Response:** `200 OK` con mensaje de confirmación o `404 NOT FOUND`

#### **GET** `/paciente/buscar-email`
Busca un paciente por email.

**Request Body:** `"email@example.com"`

**Response:** `200 OK` o `404 NOT FOUND`

---

### 🦷 Odontólogos

#### **GET** `/odontologo`
Lista todos los odontólogos.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nombre": "Luciano",
    "apellido": "Sicolo",
    "matricula": 12345
  }
]
```

#### **GET** `/odontologo/{id}`
Busca un odontólogo por ID.

**Response:** `200 OK` o `404 NOT FOUND`

#### **POST** `/odontologo/crear`
Crea un nuevo odontólogo.

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "matricula": 54321
}
```

**Response:** `200 OK` con el odontólogo creado o `400 BAD REQUEST` si la matrícula ya existe

#### **PUT** `/odontologo/modificar/{id}`
Modifica un odontólogo existente.

**Request Body:** Mismo formato que crear

**Response:** `200 OK` o `404 NOT FOUND`

#### **DELETE** `/odontologo/eliminar/{id}`
Elimina un odontólogo.

**Response:** `200 OK` con mensaje de confirmación o `404 NOT FOUND`

#### **GET** `/odontologo/buscar-nombre`
Busca un odontólogo por nombre.

**Request Body:** `"Luciano"`

**Response:** `200 OK` o `404 NOT FOUND`

---

### 📅 Turnos

#### **GET** `/turno/todos`
Lista todos los turnos.

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "fecha": "2024-02-15",
    "paciente": {
      "id": 1,
      "nombre": "Homero",
      "apellido": "Simpson",
      ...
    },
    "odontologo": {
      "id": 1,
      "nombre": "Luciano",
      "apellido": "Sicolo",
      ...
    }
  }
]
```

#### **GET** `/turno/{id}`
Busca un turno por ID.

**Response:** `200 OK` o `404 NOT FOUND`

#### **POST** `/turno/crear`
Crea un nuevo turno. **Valida** que el paciente y odontólogo existan.

**Request Body:**
```json
{
  "fecha": "2024-02-15",
  "paciente": {
    "id": 1
  },
  "odontologo": {
    "id": 1
  }
}
```

**Response:** `200 OK` con el turno creado o `404 NOT FOUND` si paciente/odontólogo no existe

#### **PUT** `/turno/modificar/{id}`
Modifica un turno existente.

**Request Body:** Mismo formato que crear

**Response:** `200 OK` o `404 NOT FOUND`

#### **DELETE** `/turno/eliminar/{id}`
Elimina un turno.

**Response:** `200 OK` con mensaje de confirmación, `404 NOT FOUND` o `500 INTERNAL SERVER ERROR`

---

## 🔧 Capa de Servicios

### **OdontologoService**
Implementa `IOdontologoService`. Utiliza `ObjectMapper` para convertir entre Entidad y DTO.

**Métodos:**
- `listarOdontologos()`: Lista todos los odontólogos
- `guardarOdontologo(OdontologoDTO)`: Crea un nuevo odontólogo
- `buscarOdontologoPorId(Long)`: Busca por ID, lanza `NotFoundException` si no existe
- `buscarOdontologoPorNombre(String)`: Busca por nombre, lanza `NotFoundException` si no existe
- `editarOdontologos(Long, OdontologoDTO)`: Actualiza un odontólogo existente
- `eliminarOdontologo(Long)`: Elimina un odontólogo

### **PacienteService**
Implementa `IPacienteService`. Valida email único antes de crear.

**Métodos:**
- `listarPacientes()`: Lista todos los pacientes
- `guardarPaciente(PacienteDTO)`: Crea un nuevo paciente, lanza `BadRequestException` si el email existe
- `buscarPacientePorId(Long)`: Busca por ID
- `buscarPacientePorEmail(String)`: Busca por email
- `editarPaciente(Long, PacienteDTO)`: Actualiza un paciente
- `eliminarPaciente(Long)`: Elimina un paciente

### **TurnoService**
Implementa `ITurnoService`. Valida existencia de paciente y odontólogo antes de crear/editar.

**Métodos:**
- `listarTurnos()`: Lista todos los turnos (método adicional, no en interfaz)
- `guardarTurno(TurnoDTO)`: Crea un nuevo turno validando paciente y odontólogo
- `buscarTurnoPorId(Long)`: Busca por ID
- `editarTurno(Long, TurnoDTO)`: Actualiza un turno validando relaciones
- `eliminarTurnoPorId(Long)`: Elimina un turno

### **UsuarioService**
Implementa `UserDetailsService` de Spring Security para autenticación.

**Métodos:**
- `loadUserByUsername(String email)`: Carga usuario por email para autenticación
- `registrarNuevoUsuario(...)`: Registra un nuevo usuario con password codificado, rol `ROLE_USER` por defecto

---

## 🗃️ Repositorios (Spring Data JPA)

Todos los repositorios extienden `JpaRepository<Entity, Long>`:

### **OdontologoRepository**
- `findByNombre(String)`: Busca odontólogo por nombre (Optional)

### **PacienteRepository**
- `findByEmail(String)`: Busca paciente por email (Optional)

### **TurnoRepository**
- Métodos CRUD estándar heredados de JpaRepository

### **UsuarioRepository**
- `findByEmail(String)`: Busca usuario por email (Optional)

---

## ⚠️ Manejo de Excepciones

### Excepciones Personalizadas

#### **NotFoundException**
Lanzada cuando no se encuentra un recurso.
- **HTTP Status**: `404 NOT FOUND`
- **Mensaje**: Descriptivo del recurso no encontrado

#### **BadRequestException**
Lanzada para solicitudes inválidas (ej: email duplicado).
- **HTTP Status**: `400 BAD REQUEST`
- **Mensaje**: Descripción del error

### **ExceptionController** (@ControllerAdvice)

Maneja globalmente las excepciones y retorna `ExceptionDTO`:

```json
{
  "message": "No se encontró el paciente con el ID 999"
}
```

---

## 🚀 Instalación y Ejecución

### Prerequisitos

- **Java 21** o superior
- **Maven 3.6+** (incluido `mvnw` wrapper)
- Navegador web moderno

### Pasos para Ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd clinica_odontologica
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```
   
   O usando el wrapper:
   ```bash
   ./mvnw clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```
   
   O usando el wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acceder a la aplicación**
   - **API Base**: `http://localhost:8080`
   - **Login**: `http://localhost:8080/login.html`
   - **Registro**: `http://localhost:8080/register.html`
   - **H2 Console**: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:~/test`
     - Username: `sa`
     - Password: `sa`

5. **Credenciales de Acceso Iniciales**
   - **Email**: `juan@mail.com`
   - **Password**: `admin`
   - **Rol**: `ROLE_ADMIN`

---

## 🧪 Testing

### Ejecutar Tests

```bash
mvn test
```

### Tests Incluidos

- **ClinicaOdontologicaApplicationTests**: Test de contexto de Spring
- **OdontologoTest**: Tests unitarios de Odontólogo
- **PacienteTest**: Tests unitarios de Paciente
- **TurnoTest**: Tests unitarios de Turno
- **OdontologoIntegrationTest**: Tests de integración
- **PacienteIntegrationTest**: Tests de integración
- **TurnoIntegrationTest**: Tests de integración

Los tests utilizan **Spring Security Test** para simular autenticación cuando es necesario.

---

## 🌐 Interfaz Web

El proyecto incluye una interfaz web en `src/main/resources/static/`:

### Páginas HTML

- **index.html**: Página principal
- **login.html**: Página de login
- **register.html**: Página de registro
- **pacienteLista.html**: Lista de pacientes
- **odontologoLista.html**: Lista de odontólogos
- **turnoLista.html**: Lista de turnos
- **datosPacientes.html**, **datosOdontologos.html**, **datosTurno.html**: Formularios

### JavaScript

- **js/navbarLoader.js**: Carga dinámica del navbar
- **js/paciente/**: Funciones para gestión de pacientes
- **js/odontologo/**: Funciones para gestión de odontólogos
- **js/Turno/**: Funciones para gestión de turnos

### Estilos

- **css/styles.css**: Estilos generales

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas** típica de Spring Boot:

```
┌─────────────────────────────────────┐
│         Controllers (REST)          │  ← Capa de Presentación
├─────────────────────────────────────┤
│         Services (Lógica)           │  ← Capa de Negocio
├─────────────────────────────────────┤
│      Repositories (JPA)             │  ← Capa de Acceso a Datos
├─────────────────────────────────────┤
│      Entities (Modelo)              │  ← Capa de Dominio
└─────────────────────────────────────┘
```

### Patrones de Diseño Utilizados

1. **Repository Pattern**: Spring Data JPA abstrae el acceso a datos
2. **Service Layer Pattern**: Separación de lógica de negocio
3. **DTO Pattern**: Transferencia de datos entre capas
4. **Dependency Injection**: Inyección de dependencias con `@Autowired`
5. **Strategy Pattern**: `UserDetailsService` para autenticación
6. **Exception Handling Pattern**: Manejo centralizado con `@ControllerAdvice`

---

## 📝 DTOs (Data Transfer Objects)

### **OdontologoDTO**
- `id`, `nombre`, `apellido`, `matricula`
- Excluye la relación `turnos` para evitar recursión

### **PacienteDTO**
- `id`, `nombre`, `apellido`, `numeroContacto`, `fechaIngreso`, `email`, `domicilio`
- Incluye objeto `Domicilio` completo
- Excluye la relación `turnos`

### **TurnoDTO**
- `id`, `fecha`, `paciente` (PacienteDTO), `odontologo` (OdontologoDTO)
- Incluye DTOs anidados de paciente y odontólogo

### **RegistroDTO**
- `nombre`, `apellido`, `userName`, `email`, `password`
- Usado para registro de nuevos usuarios

### **ExceptionDTO**
- `message`: Mensaje de error

---

## 🔍 Validaciones y Reglas de Negocio

### Pacientes
- El **email debe ser único** (validado en `PacienteService.guardarPaciente()`)
- Relación **OneToOne** con Domicilio (cascade ALL)

### Odontólogos
- La **matrícula debe ser única** (constraint a nivel de entidad)
- Relación **OneToMany** con Turnos (LAZY loading)

### Turnos
- **Debe existir** el Paciente referenciado (validado en servicio)
- **Debe existir** el Odontólogo referenciado (validado en servicio)

### Usuarios
- El **email debe ser único** (constraint a nivel de entidad)
- **Password encriptado** con BCrypt antes de guardar
- **Rol por defecto**: `ROLE_USER` para nuevos registros

---

## 🐛 Debugging y Consola H2

### Acceder a la Consola H2

1. Ejecutar la aplicación
2. Navegar a: `http://localhost:8080/h2-console`
3. Configuración:
   - **JDBC URL**: `jdbc:h2:~/test`
   - **Username**: `sa`
   - **Password**: `sa`
4. Click en "Connect"

### Consultas Útiles

```sql
-- Ver todos los pacientes
SELECT * FROM pacientes;

-- Ver todos los odontólogos
SELECT * FROM odontologos;

-- Ver todos los turnos con información de paciente y odontólogo
SELECT t.*, p.nombre as paciente_nombre, o.nombre as odontologo_nombre
FROM turnos t
JOIN pacientes p ON t.paciente_id = p.id
JOIN odontologos o ON t.odontologo_id = o.id;

-- Ver todos los usuarios
SELECT * FROM usuarios;
```

---

## 📚 Dependencias Maven Clave

```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

---

## ⚙️ Configuración Avanzada

### Cambiar el Puerto

En `application.properties`:
```properties
server.port=8081
```

### Cambiar Estrategia de Base de Datos

De `create-drop` a `update` para persistir datos entre reinicios:
```properties
spring.jpa.hibernate.ddl-auto=update
```

### Habilitar Logs SQL

Ya está habilitado:
```properties
spring.jpa.show-sql=true
```

---

## 🚨 Notas Importantes

1. **CSRF Deshabilitado**: En producción, habilitar CSRF para mayor seguridad
2. **Base de Datos en Memoria**: Los datos se pierden al reiniciar la aplicación
3. **Password Hardcodeado**: El usuario admin se crea con password `admin` (cambiar en producción)
4. **Log4j Versión Antigua**: Se recomienda actualizar a Log4j2 o usar el logging de Spring Boot

---

## 👥 Autores

- **Juan Ignacio Bellavitis**
- **Luciano Sicolo**