# 🦷 Sistema de Gestión para Clínica Odontológica

Sistema de gestión desarrollado con Spring Boot para administrar pacientes, odontólogos y domicilios de una clínica odontológica.

## 📋 Descripción

Este proyecto es una aplicación backend construida con Spring Boot que implementa un sistema CRUD completo para la gestión de una clínica odontológica. Utiliza el patrón DAO (Data Access Object) para la persistencia de datos y una base de datos H2 en memoria para el almacenamiento.

## ✨ Características

- ✅ Gestión completa de **Pacientes** (CRUD)
- ✅ Gestión completa de **Odontólogos** (CRUD)
- ✅ Gestión de **Domicilios**
- ✅ Búsqueda por ID y por nombre
- ✅ Base de datos H2 en memoria
- ✅ Patrón DAO para acceso a datos
- ✅ Uso de interfaces para desacoplamiento
- ✅ Tests unitarios incluidos
- ✅ Lombok para reducir código boilerplate

## 🛠️ Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
    - Spring Boot Starter Web
    - Spring Boot DevTools
    - Spring Boot Starter Test
- **H2 Database** (base de datos en memoria)
- **Lombok**
- **Maven** (gestión de dependencias)

## 📁 Estructura del Proyecto

```
clinica_odontologica/
├── src/
│   ├── main/
│   │   ├── java/com/clinica_odontologica/clinica_odontologica/
│   │   │   ├── ClinicaOdontologicaApplication.java  # Clase principal
│   │   │   ├── dao/                                  # Capa de acceso a datos
│   │   │   │   ├── BD.java                          # Configuración BD
│   │   │   │   ├── IDAO.java                        # Interfaz DAO genérica
│   │   │   │   ├── PacienteDAOH2.java              # DAO Pacientes
│   │   │   │   ├── OdontologoDAOH2.java            # DAO Odontólogos
│   │   │   │   └── DomicilioDAOH2.java             # DAO Domicilios
│   │   │   ├── model/                               # Modelos de datos
│   │   │   │   ├── Paciente.java
│   │   │   │   ├── Odontologo.java
│   │   │   │   └── Domicilio.java
│   │   │   └── service/                             # Capa de servicios
│   │   │       ├── ISERVICE.java                    # Interfaz Service genérica
│   │   │       ├── PacienteService.java
│   │   │       └── OdontologoService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/clinica_odontologica/clinica_odontologica/
│           ├── ClinicaOdontologicaApplicationTests.java
│           ├── PacienteTest.java
│           └── OdontologoTest.java
├── pom.xml
└── README.md
```

## 🚀 Instalación y Ejecución

### Prerequisitos

- Java 21 o superior
- Maven 3.6+

### Pasos para ejecutar

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd clinica_odontologica
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

   O usando el wrapper de Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. La aplicación iniciará en `http://localhost:8080`

## 💾 Base de Datos

El proyecto utiliza **H2 Database** en memoria con las siguientes tablas:

### Tabla PACIENTES
- `ID` (PK, Auto-increment)
- `NOMBRE` (VARCHAR)
- `APELLIDO` (VARCHAR)
- `NUMEROCONTACTO` (INT)
- `FECHAINGRESO` (DATE)
- `DOMICILIO_ID` (FK)
- `EMAIL` (VARCHAR)

### Tabla ODONTOLOGOS
- `ID` (PK, Auto-increment)
- `NOMBRE` (VARCHAR)
- `APELLIDO` (VARCHAR)
- `MATRICULA` (INT)

### Tabla DOMICILIOS
- `ID` (PK, Auto-increment)
- `CALLE` (VARCHAR)
- `NUMERO` (INT)
- `LOCALIDAD` (VARCHAR)
- `PROVINCIA` (VARCHAR)

### Datos de Prueba

La base de datos se inicializa automáticamente con datos de ejemplo:

**Pacientes:**
- Homero Simpson
- Marge Simpson

**Odontólogos:**
- Luciano Sicolo
- Juan Pérez

**Domicilios:**
- Siempre Viva 723, Springfield, USA
- Calle Falsa 123, Springfield, USA

## 🔧 Funcionalidades Principales

### Gestión de Pacientes
- ✅ Crear nuevo paciente
- ✅ Buscar paciente por ID
- ✅ Buscar paciente por nombre
- ✅ Listar todos los pacientes
- ✅ Modificar datos de paciente
- ✅ Eliminar paciente

### Gestión de Odontólogos
- ✅ Crear nuevo odontólogo
- ✅ Buscar odontólogo por ID
- ✅ Buscar odontólogo por nombre
- ✅ Listar todos los odontólogos
- ✅ Modificar datos de odontólogo
- ✅ Eliminar odontólogo

## 🧪 Testing

Para ejecutar los tests:

```bash
mvn test
```

Los tests incluyen:
- Tests de servicios de Pacientes
- Tests de servicios de Odontólogos
- Tests de la aplicación principal

## 📝 Ejemplo de Uso

La clase `ClinicaOdontologicaApplication` contiene ejemplos de todas las operaciones CRUD:

```java
// Crear un nuevo paciente
Paciente paciente = new Paciente("Bart", "Simpson", 11223344, "1", 
    "bart@disney.com", LocalDate.of(2025, 10, 9));
pacienteService.guardar(paciente);

// Buscar paciente por ID
Paciente encontrado = pacienteService.buscar(2);

// Buscar por nombre
Paciente porNombre = pacienteService.buscarPorNombre("Marge");

// Listar todos
List<Paciente> todos = pacienteService.buscarTodos();

// Modificar paciente
paciente.setNombre("Homer");
pacienteService.modificar(paciente);

// Eliminar paciente
pacienteService.eliminar(2);
```

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas:

1. **Capa de Modelo** (`model/`): Define las entidades del dominio
2. **Capa de Acceso a Datos** (`dao/`): Maneja la persistencia con la base de datos
3. **Capa de Servicio** (`service/`): Contiene la lógica de negocio
4. **Capa de Aplicación**: Clase principal que orquesta las operaciones

### Patrones de Diseño Utilizados

- **DAO (Data Access Object)**: Separación entre lógica de negocio y persistencia
- **Dependency Injection**: A través de constructores
- **Interface Segregation**: Interfaces genéricas `IDAO<T>` e `ISERVICE<T>`

## 👥 Autore

Juan Ignacio Bellavitis
Luciano Sicolo

---