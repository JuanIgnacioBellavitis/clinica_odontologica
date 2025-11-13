package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import com.clinica_odontologica.clinica_odontologica.service.TurnoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TurnoIntegrationTest {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @DisplayName("Integración: Crear turno correctamente")
    public void crearTurno_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(12345);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setNumeroContacto(11223344);
        paciente.setEmail("lisa@disney.com");
        paciente.setFechaIngreso(LocalDate.of(2025, 10, 9));
        paciente.setDomicilio(domicilio);
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Crear turno
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        // Act
        TurnoDTO guardado = turnoService.guardarTurno(turno);

        // Assert
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertNotNull(guardado.getFecha());
        Assertions.assertNotNull(guardado.getPaciente());
        Assertions.assertNotNull(guardado.getOdontologo());
        Assertions.assertEquals(pacienteGuardado.getId(), guardado.getPaciente().getId());
        Assertions.assertEquals(odontologoGuardado.getId(), guardado.getOdontologo().getId());
    }

    @Test
    @DisplayName("Integración: Crear turno con paciente inexistente debe fallar")
    public void crearTurno_PacienteInexistente_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(11111);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Paciente inexistente
        PacienteDTO pacienteInexistente = new PacienteDTO();
        pacienteInexistente.setId(99999L);

        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteInexistente);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            turnoService.guardarTurno(turno);
        });
    }

    @Test
    @DisplayName("Integración: Crear turno con odontólogo inexistente debe fallar")
    public void crearTurno_OdontologoInexistente_Integration() {
        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail("lisa@disney.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Odontólogo inexistente
        OdontologoDTO odontologoInexistente = new OdontologoDTO();
        odontologoInexistente.setId(99999L);

        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoInexistente);
        turno.setFecha(LocalDate.now());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            turnoService.guardarTurno(turno);
        });
    }

    @Test
    @DisplayName("Integración: Listar todos los turnos")
    public void listarTurnos_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(22222);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail("lisa2@disney.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Crear turnos
        TurnoDTO turno1 = new TurnoDTO();
        turno1.setPaciente(pacienteGuardado);
        turno1.setOdontologo(odontologoGuardado);
        turno1.setFecha(LocalDate.now());

        TurnoDTO turno2 = new TurnoDTO();
        turno2.setPaciente(pacienteGuardado);
        turno2.setOdontologo(odontologoGuardado);
        turno2.setFecha(LocalDate.now().plusDays(1));

        turnoService.guardarTurno(turno1);
        turnoService.guardarTurno(turno2);

        // Act
        List<TurnoDTO> turnos = turnoService.listarTurnos();

        // Assert
        Assertions.assertNotNull(turnos);
        Assertions.assertTrue(turnos.size() >= 2);
    }

    @Test
    @DisplayName("Integración: Buscar turno por ID")
    public void buscarTurnoPorId_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(33333);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail("lisa3@disney.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Crear turno
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        LocalDate fecha = LocalDate.now();
        turno.setFecha(fecha);

        TurnoDTO guardado = turnoService.guardarTurno(turno);

        // Act
        TurnoDTO encontrado = turnoService.buscarTurnoPorId(guardado.getId());

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(guardado.getId(), encontrado.getId());
        Assertions.assertEquals(fecha, encontrado.getFecha());
        Assertions.assertNotNull(encontrado.getPaciente());
        Assertions.assertNotNull(encontrado.getOdontologo());
    }

    @Test
    @DisplayName("Integración: Buscar turno por ID - No encontrado")
    public void buscarTurnoPorId_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> turnoService.buscarTurnoPorId(99999L)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró"));
    }

    @Test
    @DisplayName("Integración: Modificar fecha de un turno")
    public void modificarTurno_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(44444);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail("lisa4@disney.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Crear turno
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        TurnoDTO guardado = turnoService.guardarTurno(turno);

        // Act
        LocalDate nuevaFecha = LocalDate.now().plusDays(5);
        guardado.setFecha(nuevaFecha);
        TurnoDTO modificado = turnoService.editarTurno(guardado.getId(), guardado);

        // Assert
        Assertions.assertNotNull(modificado);
        Assertions.assertEquals(nuevaFecha, modificado.getFecha());
        Assertions.assertEquals(guardado.getId(), modificado.getId());
    }

    @Test
    @DisplayName("Integración: Modificar turno cambiando paciente y odontólogo")
    public void modificarTurno_CambiarPacienteYOdontologo_Integration() {
        // Arrange - Crear odontólogos
        OdontologoDTO odontologo1 = new OdontologoDTO();
        odontologo1.setNombre("Homero");
        odontologo1.setApellido("Simpson");
        odontologo1.setMatricula(55555);
        OdontologoDTO odontologo1Guardado = odontologoService.guardarOdontologo(odontologo1);

        OdontologoDTO odontologo2 = new OdontologoDTO();
        odontologo2.setNombre("Bart");
        odontologo2.setApellido("Simpson");
        odontologo2.setMatricula(66666);
        OdontologoDTO odontologo2Guardado = odontologoService.guardarOdontologo(odontologo2);

        // Arrange - Crear pacientes
        Domicilio domicilio1 = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente1 = new PacienteDTO();
        paciente1.setNombre("Lisa");
        paciente1.setApellido("Simpson");
        paciente1.setEmail("lisa5@disney.com");
        paciente1.setDomicilio(domicilio1);
        paciente1.setFechaIngreso(LocalDate.now());
        PacienteDTO paciente1Guardado = pacienteService.guardarPaciente(paciente1);

        Domicilio domicilio2 = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");
        PacienteDTO paciente2 = new PacienteDTO();
        paciente2.setNombre("Marge");
        paciente2.setApellido("Bouvier");
        paciente2.setEmail("marge@springfield.com");
        paciente2.setDomicilio(domicilio2);
        paciente2.setFechaIngreso(LocalDate.now());
        PacienteDTO paciente2Guardado = pacienteService.guardarPaciente(paciente2);

        // Arrange - Crear turno inicial
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(paciente1Guardado);
        turno.setOdontologo(odontologo1Guardado);
        turno.setFecha(LocalDate.now());

        TurnoDTO guardado = turnoService.guardarTurno(turno);

        // Act - Modificar turno
        guardado.setPaciente(paciente2Guardado);
        guardado.setOdontologo(odontologo2Guardado);
        TurnoDTO modificado = turnoService.editarTurno(guardado.getId(), guardado);

        // Assert
        Assertions.assertNotNull(modificado);
        Assertions.assertEquals(paciente2Guardado.getId(), modificado.getPaciente().getId());
        Assertions.assertEquals(odontologo2Guardado.getId(), modificado.getOdontologo().getId());
    }

    @Test
    @DisplayName("Integración: Eliminar un turno existente")
    public void eliminarTurno_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(77777);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail("lisa6@disney.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Crear turno
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        TurnoDTO guardado = turnoService.guardarTurno(turno);
        Long id = guardado.getId();

        // Act
        String mensaje = turnoService.eliminarTurnoPorId(id);

        // Assert
        Assertions.assertNotNull(mensaje);
        Assertions.assertTrue(mensaje.contains("eliminado") || mensaje.contains("Eliminado"));

        // Verificar que ya no existe
        Assertions.assertThrows(NotFoundException.class, () -> {
            turnoService.buscarTurnoPorId(id);
        });
    }

    @Test
    @DisplayName("Integración: Eliminar turno - No encontrado")
    public void eliminarTurno_NoEncontrado_Integration() {
        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> turnoService.eliminarTurnoPorId(99999L)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el turno con ID"));
    }

    @Test
    @DisplayName("Integración: Flujo completo CRUD de turno")
    public void flujoCompletoCRUD_Integration() {
        // CREATE - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Ned");
        odontologo.setApellido("Flanders");
        odontologo.setMatricula(88888);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // CREATE - Crear paciente
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Ned");
        paciente.setApellido("Flanders");
        paciente.setEmail("ned@springfield.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // CREATE - Crear turno
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        TurnoDTO creado = turnoService.guardarTurno(turno);
        Assertions.assertNotNull(creado.getId());
        Long id = creado.getId();

        // READ
        TurnoDTO leido = turnoService.buscarTurnoPorId(id);
        Assertions.assertNotNull(leido);
        Assertions.assertEquals(id, leido.getId());

        // UPDATE
        LocalDate nuevaFecha = LocalDate.now().plusDays(10);
        leido.setFecha(nuevaFecha);
        TurnoDTO actualizado = turnoService.editarTurno(id, leido);
        Assertions.assertEquals(nuevaFecha, actualizado.getFecha());

        // DELETE
        turnoService.eliminarTurnoPorId(id);
        Assertions.assertThrows(NotFoundException.class, () -> {
            turnoService.buscarTurnoPorId(id);
        });
    }

    @Test
    @DisplayName("Integración: Relaciones turno-paciente-odontólogo se persisten correctamente")
    public void relacionesTurno_Integration() {
        // Arrange - Crear odontólogo
        OdontologoDTO odontologo = new OdontologoDTO();
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");
        odontologo.setMatricula(99999);
        OdontologoDTO odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // Arrange - Crear paciente
        Domicilio domicilio = new Domicilio("Siempre Viva", 723, "Springfield", "USA");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail("lisa7@disney.com");
        paciente.setDomicilio(domicilio);
        paciente.setFechaIngreso(LocalDate.now());
        PacienteDTO pacienteGuardado = pacienteService.guardarPaciente(paciente);

        // Arrange - Crear turno
        TurnoDTO turno = new TurnoDTO();
        turno.setPaciente(pacienteGuardado);
        turno.setOdontologo(odontologoGuardado);
        turno.setFecha(LocalDate.now());

        // Act
        TurnoDTO guardado = turnoService.guardarTurno(turno);

        // Assert - Verificar relaciones
        Assertions.assertNotNull(guardado.getPaciente());
        Assertions.assertEquals(pacienteGuardado.getId(), guardado.getPaciente().getId());
        Assertions.assertEquals("Lisa", guardado.getPaciente().getNombre());

        Assertions.assertNotNull(guardado.getOdontologo());
        Assertions.assertEquals(odontologoGuardado.getId(), guardado.getOdontologo().getId());
        Assertions.assertEquals("Homero", guardado.getOdontologo().getNombre());
    }
}

