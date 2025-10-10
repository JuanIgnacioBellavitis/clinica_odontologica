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
    @DisplayName("Eliminar paciente existente")
    public void eliminarPaciente() {
        System.out.println("************ ELIMINAR PACIENTE ************");
        // Arrange
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // Act
        // Comprobamos que inicialmente hay 2 pacientes
        List<Paciente> antes = pacienteService.buscarPacientes();
        Assertions.assertEquals(2, antes.size());

        // Eliminamos al paciente con ID 1
        List<Paciente> despues = pacienteService.eliminarPaciente(1);

        // Validamos que la lista ahora tiene 1 menos
        Assertions.assertEquals(1, despues.size());

        // Assert
        // Verificamos que no exista el ID 1
        Paciente eliminado = despues.stream()
                .filter(p -> p.getId() == 1)
                .findFirst()
                .orElse(null);


        Assertions.assertNull(eliminado);
        System.out.println("********************************************");
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
}
