package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class PacienteTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @DisplayName("Guardar paciente correctamente")
    public void GuardarPaciente() {
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(11223344);
        dto.setEmail("lisa@disney.com");
        dto.setFechaIngreso(LocalDate.of(2025, 10, 9));
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
        Assertions.assertEquals("Simpson", guardado.getApellido());
    }

    @Test
    @DisplayName("Listar pacientes existentes")
    public void ListarPacientes() {
        List<PacienteDTO> lista = pacienteService.listarPacientes();
        Assertions.assertNotNull(lista);
    }

    @Test
    @DisplayName("Buscar paciente por ID")
    public void testBuscarPorId() {
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Homero");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(22334455);
        dto.setEmail("homero@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);
        PacienteDTO encontrado = pacienteService.buscarPacientePorId(guardado.getId());

        Assertions.assertEquals("Homero", encontrado.getNombre());
    }

    @Test
    @DisplayName("Modificar paciente existente")
    public void ModificarPaciente() {
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Bart");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(33445566);
        dto.setEmail("bart@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        guardado.setNombre("Bartolomeo");
        guardado.setEmail("barto@springfield.com");
        PacienteDTO actualizado = pacienteService.editarPaciente(guardado.getId(), guardado);

        Assertions.assertEquals("Bartolomeo", actualizado.getNombre());
        Assertions.assertEquals("barto@springfield.com", actualizado.getEmail());
    }

    @Test
    @DisplayName("Eliminar paciente correctamente")
    public void EliminarPaciente() {
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Marge");
        dto.setApellido("Bouvier");
        dto.setNumeroContacto(44556677);
        dto.setEmail("marge@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        String msj = pacienteService.eliminarPaciente(guardado.getId());
        Assertions.assertTrue(msj.contains("eliminado") || msj.contains("Eliminado"));
    }
}
