package com.clinica_odontologica.clinica_odontologica;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import com.clinica_odontologica.clinica_odontologica.exceptions.BadRequestException;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PacienteIntegrationTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @DisplayName("Integración: Guardar paciente correctamente")
    public void guardarPaciente_Integration() {
        // Arrange
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(11223344);
        dto.setEmail("lisa@disney.com");
        dto.setFechaIngreso(LocalDate.of(2025, 10, 9));
        dto.setDomicilio(domicilio);

        // Act
        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        // Assert
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
        Assertions.assertEquals("Simpson", guardado.getApellido());
        Assertions.assertEquals("lisa@disney.com", guardado.getEmail());
        Assertions.assertNotNull(guardado.getDomicilio());
    }

    @Test
    @DisplayName("Integración: Guardar paciente con email duplicado debe fallar")
    public void guardarPaciente_EmailDuplicado_Integration() {
        // Arrange
        Domicilio domicilio1 = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        Domicilio domicilio2 = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto1 = new PacienteDTO();
        dto1.setNombre("Homero");
        dto1.setApellido("Simpson");
        dto1.setEmail("homero@springfield.com");
        dto1.setDomicilio(domicilio1);
        dto1.setFechaIngreso(LocalDate.now());

        PacienteDTO dto2 = new PacienteDTO();
        dto2.setNombre("Bart");
        dto2.setApellido("Simpson");
        dto2.setEmail("homero@springfield.com");
        dto2.setDomicilio(domicilio2);
        dto2.setFechaIngreso(LocalDate.now());

        // Act
        pacienteService.guardarPaciente(dto1);

        // Assert
        BadRequestException exception = Assertions.assertThrows(
            BadRequestException.class,
            () -> pacienteService.guardarPaciente(dto2)
        );

        Assertions.assertTrue(exception.getMessage().contains("Ya existe un paciente registrado con el email"));
    }

    @Test
    @DisplayName("Integración: Listar pacientes existentes")
    public void listarPacientes_Integration() {
        // Arrange
        Domicilio domicilio1 = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        Domicilio domicilio2 = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto1 = new PacienteDTO();
        dto1.setNombre("Lisa");
        dto1.setApellido("Simpson");
        dto1.setEmail("lisa@disney.com");
        dto1.setDomicilio(domicilio1);
        dto1.setFechaIngreso(LocalDate.now());

        PacienteDTO dto2 = new PacienteDTO();
        dto2.setNombre("Homero");
        dto2.setApellido("Simpson");
        dto2.setEmail("homero@springfield.com");
        dto2.setDomicilio(domicilio2);
        dto2.setFechaIngreso(LocalDate.now());

        pacienteService.guardarPaciente(dto1);
        pacienteService.guardarPaciente(dto2);

        // Act
        List<PacienteDTO> lista = pacienteService.listarPacientes();

        // Assert
        Assertions.assertNotNull(lista);
        Assertions.assertTrue(lista.size() >= 2);
    }

    @Test
    @DisplayName("Integración: Buscar paciente por ID")
    public void buscarPorId_Integration() {
        // Arrange
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Homero");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(22334455);
        dto.setEmail("homero@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        // Act
        PacienteDTO encontrado = pacienteService.buscarPacientePorId(guardado.getId());

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Homero", encontrado.getNombre());
        Assertions.assertEquals("Simpson", encontrado.getApellido());
        Assertions.assertEquals("homero@springfield.com", encontrado.getEmail());
    }

    @Test
    @DisplayName("Integración: Buscar paciente por ID - No encontrado")
    public void buscarPorId_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> pacienteService.buscarPacientePorId(99999L)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el paciente con el ID"));
    }

    @Test
    @DisplayName("Integración: Buscar paciente por email")
    public void buscarPorEmail_Integration() {
        // Arrange
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setEmail("lisa@disney.com");
        dto.setDomicilio(domicilio);
        dto.setFechaIngreso(LocalDate.now());

        pacienteService.guardarPaciente(dto);

        // Act
        PacienteDTO encontrado = pacienteService.buscarPacientePorEmail("lisa@disney.com");

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Lisa", encontrado.getNombre());
        Assertions.assertEquals("lisa@disney.com", encontrado.getEmail());
    }

    @Test
    @DisplayName("Integración: Buscar paciente por email - No encontrado")
    public void buscarPorEmail_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> pacienteService.buscarPacientePorEmail("noexiste@email.com")
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontro el paciente con el email"));
    }

    @Test
    @DisplayName("Integración: Modificar paciente existente")
    public void modificarPaciente_Integration() {
        // Arrange
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Bart");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(33445566);
        dto.setEmail("bart@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        // Act
        guardado.setNombre("Bartolomeo");
        guardado.setEmail("barto@springfield.com");
        PacienteDTO actualizado = pacienteService.editarPaciente(guardado.getId(), guardado);

        // Assert
        Assertions.assertNotNull(actualizado);
        Assertions.assertEquals("Bartolomeo", actualizado.getNombre());
        Assertions.assertEquals("barto@springfield.com", actualizado.getEmail());
        Assertions.assertEquals(guardado.getId(), actualizado.getId());
    }

    @Test
    @DisplayName("Integración: Eliminar paciente correctamente")
    public void eliminarPaciente_Integration() {
        // Arrange
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Marge");
        dto.setApellido("Bouvier");
        dto.setNumeroContacto(44556677);
        dto.setEmail("marge@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);
        Long id = guardado.getId();

        // Act
        String msj = pacienteService.eliminarPaciente(id);

        // Assert
        Assertions.assertNotNull(msj);
        Assertions.assertTrue(msj.contains("eliminado") || msj.contains("Eliminado"));

        // Verificar que ya no existe
        Assertions.assertThrows(NotFoundException.class, () -> {
            pacienteService.buscarPacientePorId(id);
        });
    }

    @Test
    @DisplayName("Integración: Eliminar paciente - No encontrado")
    public void eliminarPaciente_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> pacienteService.eliminarPaciente(99999L)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el paciente con ID"));
    }

    @Test
    @DisplayName("Integración: Flujo completo CRUD de paciente")
    public void flujoCompletoCRUD_Integration() {
        // CREATE
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");
        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Ned");
        dto.setApellido("Flanders");
        dto.setEmail("ned@springfield.com");
        dto.setNumeroContacto(12345678);
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO creado = pacienteService.guardarPaciente(dto);
        Assertions.assertNotNull(creado.getId());
        Long id = creado.getId();

        // READ
        PacienteDTO leido = pacienteService.buscarPacientePorId(id);
        Assertions.assertEquals("Ned", leido.getNombre());

        // UPDATE
        leido.setNombre("Nedward");
        leido.setEmail("nedward@springfield.com");
        PacienteDTO actualizado = pacienteService.editarPaciente(id, leido);
        Assertions.assertEquals("Nedward", actualizado.getNombre());
        Assertions.assertEquals("nedward@springfield.com", actualizado.getEmail());

        // DELETE
        pacienteService.eliminarPaciente(id);
        Assertions.assertThrows(NotFoundException.class, () -> {
            pacienteService.buscarPacientePorId(id);
        });
    }

    @Test
    @DisplayName("Integración: Relación paciente-domicilio se persiste correctamente")
    public void relacionPacienteDomicilio_Integration() {
        // Arrange
        Domicilio domicilio = new Domicilio("Siempre Viva", 723, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Homero");
        dto.setApellido("Simpson");
        dto.setEmail("homero@springfield.com");
        dto.setDomicilio(domicilio);
        dto.setFechaIngreso(LocalDate.now());

        // Act
        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        // Assert
        Assertions.assertNotNull(guardado.getDomicilio());
        Assertions.assertEquals("Siempre Viva", guardado.getDomicilio().getCalle());
        Assertions.assertEquals(723, guardado.getDomicilio().getNumero());
        Assertions.assertEquals("Springfield", guardado.getDomicilio().getLocalidad());
    }
}

