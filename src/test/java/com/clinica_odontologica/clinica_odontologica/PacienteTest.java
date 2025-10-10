package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dao.BD;
import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.dao.PacienteDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

public class PacienteTest {
    @Test
    @DisplayName("Buscar paciente con ID")
    public void buscarPaciente(){
        System.out.println("************ BUSCAR PACIENTE ************");
        // Arrange
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // Act
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        System.out.println("Datos encontreados: " + paciente);

        // Assert
        System.out.println("Paciente encontrado: " + paciente);
        Assertions.assertTrue(paciente != null);
        Assertions.assertEquals("Simpson", paciente.getApellido());
        Assertions.assertEquals("1", paciente.getDomicilio());
        System.out.println("*****************************************");
    }

    @Test
    @DisplayName("Buscar paciente con ID inexistente")
    public void buscarPaciente_ERROR(){
        System.out.println("************ ERROR BUSCAR PACIENTE ************");
        // Arrange
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // Act
        Paciente paciente = pacienteService.buscarPacientePorId(999);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> pacienteService.buscarPacientePorId(null)
        );

        // Assert
        Assertions.assertTrue(exception.getMessage().contains("no puede ser null"));
        System.out.println("*****************************************");
    }

    @Test
    @DisplayName("Obtener listado de pacientes")
    public void obtenerTodosLosPacientes() {
        System.out.println("************ LISTAR TODOS PACIENTES ************");
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        List<Paciente> pacientesIniciales = pacienteService.buscarPacientes();

        System.out.println("Pacientes encontrados: " + pacientesIniciales);
        Assertions.assertEquals(2, pacientesIniciales.size());
        System.out.println("*************************************************");
    }

    @Test
    @DisplayName("Guardado existoso de paciente")
    public void guardarPaciente() {
        System.out.println("************ GUARDAR PACIENTES ************");
        // Arrange
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        Paciente nuevo = new Paciente("Lisa", "Simpson", 11223344,
                "1", "lisa@disney.com", LocalDate.of(2025, 10, 9));

        // Act
        Paciente guardado = pacienteService.guardarPaciente(nuevo);
        System.out.println("Paciente guardado: " + guardado);

        // Assert
        Assertions.assertNotNull(guardado);
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
        Assertions.assertEquals("Simpson", guardado.getApellido());
        System.out.println("********************************************");
    }

    @Test
    @DisplayName("Error guardando paciente con email incorrecto")
    public void guardarPaciente_ERROR() {
        System.out.println("************ ERROR GUARDAR PACIENTES ************");
        // Arrange
        BD.crearTablas();
        PacienteService service = new PacienteService(new PacienteDAOH2());
        // Forzamos error en mail
        Paciente p = new Paciente("Lisa", "Simpson", 11223344,
                "1", "lisasimpson.com", LocalDate.of(2025, 10, 9));

        // Act
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.guardarPaciente(p)
        );

        // Assert
        System.out.println(exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("email"));
        System.out.println("********************************************");
    }

    @Test
    @DisplayName("Eliminar paciente existente")
    public void eliminarPaciente() {
        System.out.println("************ ELIMINAR PACIENTE ************");
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // Comprobamos que inicialmente hay 2 pacientes
        List<Paciente> antes = pacienteService.buscarPacientes();
        Assertions.assertEquals(2, antes.size());

        // Eliminamos al paciente con ID 1
        List<Paciente> despues = pacienteService.eliminarPaciente(1);

        // Validamos que la lista ahora tiene 1 menos
        Assertions.assertEquals(1, despues.size());

        // Verificamos que no exista el ID 1
        Paciente eliminado = despues.stream()
                .filter(p -> p.getId() == 1)
                .findFirst()
                .orElse(null);

        Assertions.assertNull(eliminado);
        System.out.println("********************************************");
    }

    @Test
    @DisplayName("Eliminar paciente con ID inexistente")
    public void eliminarPaciente_conIdNull_debeLanzarExcepcion() {
        System.out.println("************ TEST ERROR: ELIMINAR ID NULL ************");
        // Arrange
        BD.crearTablas();
        PacienteService service = new PacienteService(new PacienteDAOH2());

        // Act
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.eliminarPaciente(999)
        );

        // Assert
        System.out.println(ex.getMessage());
        Assertions.assertTrue(ex.getMessage().contains("❌ No se puede eliminar: no existe un paciente con ID 999 ❌"));
        System.out.println("********************************************************");
    }

    @Test
    @DisplayName("Modificar paciente existente")
    public void modificarPaciente() {
        System.out.println("************ MODIFICAR PACIENTE ************");
        // Arrange
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // Act
        Paciente paciente = pacienteService.buscarPacientePorId(1);
        Assertions.assertNotNull(paciente);

        paciente.setNombre("Homero Jay");
        paciente.setEmail("homero.jay@springfield.com");
        paciente.setNumeroContacto(99999999);

        Paciente modificado = pacienteService.modificarPaciente(paciente);

        Paciente verificado = pacienteService.buscarPacientePorId(modificado.getId());

        // Assert
        System.out.println("Paciente modificado: " + verificado);
        Assertions.assertNotNull(verificado);
        Assertions.assertEquals("Homero Jay", verificado.getNombre());
        Assertions.assertEquals("homero.jay@springfield.com", verificado.getEmail());
        Assertions.assertEquals(99999999, verificado.getNumeroContacto());
        System.out.println("********************************************");
    }

    @Test
    @DisplayName("Error modificar paciente inexistente")
    public void modificarPaciente_ERROR() {
        System.out.println("************ ERROR MODIFICAR PACIENTE NO EXISTENTE ************");
        // Arrange
        BD.crearTablas();
        PacienteService service = new PacienteService(new PacienteDAOH2());

        // Act
        Paciente paciente = new Paciente(
                999, "Maggie", "Simpson", 123456,
                LocalDate.now(), "Springfield 742", "maggie@springfield.com"
        );

        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.modificarPaciente(paciente)
        );

        System.out.println(ex.getMessage());
        // Assert
        Assertions.assertTrue(ex.getMessage().contains("❌ No existe un paciente con ID 999 ❌"));
        System.out.println("********************************************************");

    }
}
