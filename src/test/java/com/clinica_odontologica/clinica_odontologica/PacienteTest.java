package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.exceptions.BadRequestException;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.PacienteRepository;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    @DisplayName("Guardar paciente correctamente")
    public void GuardarPaciente() {
        // Arrange
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(11223344);
        dto.setEmail("lisa@disney.com");
        dto.setFechaIngreso(LocalDate.of(2025, 10, 9));
        dto.setDomicilio(domicilio);

        Paciente entity = new Paciente();
        entity.setNombre("Lisa");
        entity.setApellido("Simpson");
        entity.setNumeroContacto(11223344);
        entity.setEmail("lisa@disney.com");
        entity.setFechaIngreso(LocalDate.of(2025, 10, 9));
        entity.setDomicilio(domicilio);

        Paciente entityGuardado = new Paciente();
        entityGuardado.setId(1L);
        entityGuardado.setNombre("Lisa");
        entityGuardado.setApellido("Simpson");
        entityGuardado.setNumeroContacto(11223344);
        entityGuardado.setEmail("lisa@disney.com");
        entityGuardado.setFechaIngreso(LocalDate.of(2025, 10, 9));
        entityGuardado.setDomicilio(domicilio);

        PacienteDTO dtoGuardado = new PacienteDTO();
        dtoGuardado.setId(1L);
        dtoGuardado.setNombre("Lisa");
        dtoGuardado.setApellido("Simpson");
        dtoGuardado.setNumeroContacto(11223344);
        dtoGuardado.setEmail("lisa@disney.com");
        dtoGuardado.setFechaIngreso(LocalDate.of(2025, 10, 9));
        dtoGuardado.setDomicilio(domicilio);

        when(pacienteRepository.findByEmail("lisa@disney.com")).thenReturn(Optional.empty());
        when(objectMapper.convertValue(dto, Paciente.class)).thenReturn(entity);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(entityGuardado);
        when(objectMapper.convertValue(entityGuardado, PacienteDTO.class)).thenReturn(dtoGuardado);

        // Act
        PacienteDTO guardado = pacienteService.guardarPaciente(dto);

        // Assert
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals("Lisa", guardado.getNombre());
        Assertions.assertEquals("Simpson", guardado.getApellido());

        // Verify
        verify(pacienteRepository, times(1)).findByEmail("lisa@disney.com");
        verify(objectMapper, times(1)).convertValue(dto, Paciente.class);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(objectMapper, times(1)).convertValue(entityGuardado, PacienteDTO.class);
    }

    @Test
    @DisplayName("Guardar paciente - Email duplicado")
    public void GuardarPaciente_EmailDuplicado() {
        // Arrange
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");

        PacienteDTO dto = new PacienteDTO();
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setEmail("lisa@disney.com");
        dto.setDomicilio(domicilio);

        Paciente pacienteExistente = new Paciente();
        pacienteExistente.setId(1L);
        pacienteExistente.setEmail("lisa@disney.com");

        when(pacienteRepository.findByEmail("lisa@disney.com")).thenReturn(Optional.of(pacienteExistente));

        // Act & Assert
        BadRequestException exception = Assertions.assertThrows(
            BadRequestException.class,
            () -> pacienteService.guardarPaciente(dto)
        );

        Assertions.assertTrue(exception.getMessage().contains("Ya existe un paciente registrado con el email"));

        // Verify
        verify(pacienteRepository, times(1)).findByEmail("lisa@disney.com");
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Listar pacientes existentes")
    public void ListarPacientes() {
        // Arrange
        Domicilio domicilio1 = new Domicilio("Calle Falsa", 123, "Springfield", "USA");
        Domicilio domicilio2 = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        paciente1.setNombre("Lisa");
        paciente1.setApellido("Simpson");
        paciente1.setEmail("lisa@disney.com");
        paciente1.setDomicilio(domicilio1);

        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);
        paciente2.setNombre("Homero");
        paciente2.setApellido("Simpson");
        paciente2.setEmail("homero@springfield.com");
        paciente2.setDomicilio(domicilio2);

        List<Paciente> pacientes = Arrays.asList(paciente1, paciente2);

        PacienteDTO dto1 = new PacienteDTO();
        dto1.setId(1L);
        dto1.setNombre("Lisa");
        dto1.setApellido("Simpson");
        dto1.setEmail("lisa@disney.com");

        PacienteDTO dto2 = new PacienteDTO();
        dto2.setId(2L);
        dto2.setNombre("Homero");
        dto2.setApellido("Simpson");
        dto2.setEmail("homero@springfield.com");

        when(pacienteRepository.findAll()).thenReturn(pacientes);
        when(objectMapper.convertValue(paciente1, PacienteDTO.class)).thenReturn(dto1);
        when(objectMapper.convertValue(paciente2, PacienteDTO.class)).thenReturn(dto2);

        // Act
        List<PacienteDTO> lista = pacienteService.listarPacientes();

        // Assert
        Assertions.assertNotNull(lista);
        Assertions.assertEquals(2, lista.size());
        Assertions.assertEquals("Lisa", lista.get(0).getNombre());
        Assertions.assertEquals("Homero", lista.get(1).getNombre());

        // Verify
        verify(pacienteRepository, times(1)).findAll();
        verify(objectMapper, times(2)).convertValue(any(Paciente.class), eq(PacienteDTO.class));
    }

    @Test
    @DisplayName("Buscar paciente por ID")
    public void testBuscarPorId() {
        // Arrange
        Long id = 1L;
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNombre("Homero");
        paciente.setApellido("Simpson");
        paciente.setNumeroContacto(22334455);
        paciente.setEmail("homero@springfield.com");
        paciente.setFechaIngreso(LocalDate.now());
        paciente.setDomicilio(domicilio);

        PacienteDTO dto = new PacienteDTO();
        dto.setId(id);
        dto.setNombre("Homero");
        dto.setApellido("Simpson");
        dto.setNumeroContacto(22334455);
        dto.setEmail("homero@springfield.com");
        dto.setFechaIngreso(LocalDate.now());
        dto.setDomicilio(domicilio);

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));
        when(objectMapper.convertValue(paciente, PacienteDTO.class)).thenReturn(dto);

        // Act
        PacienteDTO encontrado = pacienteService.buscarPacientePorId(id);

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Homero", encontrado.getNombre());
        Assertions.assertEquals("Simpson", encontrado.getApellido());

        // Verify
        verify(pacienteRepository, times(1)).findById(id);
        verify(objectMapper, times(1)).convertValue(paciente, PacienteDTO.class);
    }

    @Test
    @DisplayName("Buscar paciente por ID - No encontrado")
    public void BuscarPorId_NoEncontrado() {
        // Arrange
        Long id = 999L;
        when(pacienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> pacienteService.buscarPacientePorId(id)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el paciente con el ID " + id));

        // Verify
        verify(pacienteRepository, times(1)).findById(id);
        verify(objectMapper, never()).convertValue(any(), eq(PacienteDTO.class));
    }

    @Test
    @DisplayName("Modificar paciente existente")
    public void ModificarPaciente() {
        // Arrange
        Long id = 1L;
        Domicilio domicilio = new Domicilio("Evergreen Terrace", 742, "Springfield", "USA");

        PacienteDTO dtoActualizado = new PacienteDTO();
        dtoActualizado.setId(id);
        dtoActualizado.setNombre("Bartolomeo");
        dtoActualizado.setApellido("Simpson");
        dtoActualizado.setNumeroContacto(33445566);
        dtoActualizado.setEmail("barto@springfield.com");
        dtoActualizado.setFechaIngreso(LocalDate.now());
        dtoActualizado.setDomicilio(domicilio);

        Paciente pacienteExistente = new Paciente();
        pacienteExistente.setId(id);
        pacienteExistente.setNombre("Bart");
        pacienteExistente.setApellido("Simpson");
        pacienteExistente.setEmail("bart@springfield.com");
        pacienteExistente.setDomicilio(domicilio);

        Paciente pacienteModificado = new Paciente();
        pacienteModificado.setId(id);
        pacienteModificado.setNombre("Bartolomeo");
        pacienteModificado.setApellido("Simpson");
        pacienteModificado.setEmail("barto@springfield.com");
        pacienteModificado.setDomicilio(domicilio);

        PacienteDTO dtoResultado = new PacienteDTO();
        dtoResultado.setId(id);
        dtoResultado.setNombre("Bartolomeo");
        dtoResultado.setEmail("barto@springfield.com");

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(pacienteExistente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteModificado);
        when(objectMapper.convertValue(pacienteModificado, PacienteDTO.class)).thenReturn(dtoResultado);

        // Act
        PacienteDTO actualizado = pacienteService.editarPaciente(id, dtoActualizado);

        // Assert
        Assertions.assertNotNull(actualizado);
        Assertions.assertEquals("Bartolomeo", actualizado.getNombre());
        Assertions.assertEquals("barto@springfield.com", actualizado.getEmail());

        // Verify
        verify(pacienteRepository, times(1)).findById(id);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(objectMapper, times(1)).convertValue(pacienteModificado, PacienteDTO.class);
    }

    @Test
    @DisplayName("Eliminar paciente correctamente")
    public void EliminarPaciente() {
        // Arrange
        Long id = 1L;
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNombre("Marge");
        paciente.setApellido("Bouvier");
        paciente.setEmail("marge@springfield.com");

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));
        doNothing().when(pacienteRepository).delete(paciente);

        // Act
        String msj = pacienteService.eliminarPaciente(id);

        // Assert
        Assertions.assertNotNull(msj);
        Assertions.assertTrue(msj.contains("eliminado") || msj.contains("Eliminado"));
        Assertions.assertTrue(msj.contains(String.valueOf(id)));

        // Verify
        verify(pacienteRepository, times(1)).findById(id);
        verify(pacienteRepository, times(1)).delete(paciente);
    }

    @Test
    @DisplayName("Eliminar paciente - No encontrado")
    public void EliminarPaciente_NoEncontrado() {
        // Arrange
        Long id = 999L;
        when(pacienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> pacienteService.eliminarPaciente(id)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el paciente con ID " + id));

        // Verify
        verify(pacienteRepository, times(1)).findById(id);
        verify(pacienteRepository, never()).delete(any(Paciente.class));
    }

    @Test
    @DisplayName("Buscar paciente por email")
    public void BuscarPacientePorEmail() {
        // Arrange
        String email = "lisa@disney.com";
        Domicilio domicilio = new Domicilio("Calle Falsa", 123, "Springfield", "USA");

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");
        paciente.setEmail(email);
        paciente.setDomicilio(domicilio);

        PacienteDTO dto = new PacienteDTO();
        dto.setId(1L);
        dto.setNombre("Lisa");
        dto.setApellido("Simpson");
        dto.setEmail(email);
        dto.setDomicilio(domicilio);

        when(pacienteRepository.findByEmail(email)).thenReturn(Optional.of(paciente));
        when(objectMapper.convertValue(paciente, PacienteDTO.class)).thenReturn(dto);

        // Act
        PacienteDTO encontrado = pacienteService.buscarPacientePorEmail(email);

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Lisa", encontrado.getNombre());
        Assertions.assertEquals(email, encontrado.getEmail());

        // Verify
        verify(pacienteRepository, times(1)).findByEmail(email);
        verify(objectMapper, times(1)).convertValue(paciente, PacienteDTO.class);
    }

    @Test
    @DisplayName("Buscar paciente por email - No encontrado")
    public void BuscarPacientePorEmail_NoEncontrado() {
        // Arrange
        String email = "noexiste@email.com";
        when(pacienteRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> pacienteService.buscarPacientePorEmail(email)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontro el paciente con el email " + email));

        // Verify
        verify(pacienteRepository, times(1)).findByEmail(email);
        verify(objectMapper, never()).convertValue(any(), eq(PacienteDTO.class));
    }
}
