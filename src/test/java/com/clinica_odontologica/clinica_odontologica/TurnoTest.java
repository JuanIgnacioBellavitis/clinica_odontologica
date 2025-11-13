package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.entity.Turno;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.OdontologoRepository;
import com.clinica_odontologica.clinica_odontologica.repository.PacienteRepository;
import com.clinica_odontologica.clinica_odontologica.repository.TurnoRepository;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import com.clinica_odontologica.clinica_odontologica.service.TurnoService;
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
public class TurnoTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private OdontologoService odontologoService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TurnoService turnoService;

    @Test
    @DisplayName("Crear turno correctamente")
    public void crearTurno() {
        // Arrange
        Long odontologoId = 1L;
        Long pacienteId = 1L;
        LocalDate fecha = LocalDate.now();

        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(odontologoId);
        odontologoDTO.setNombre("Homero");
        odontologoDTO.setApellido("Simpson");
        odontologoDTO.setMatricula(12345);

        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(pacienteId);
        pacienteDTO.setNombre("Lisa");
        pacienteDTO.setApellido("Simpson");
        pacienteDTO.setEmail("lisa@disney.com");

        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setPaciente(pacienteDTO);
        turnoDTO.setOdontologo(odontologoDTO);
        turnoDTO.setFecha(fecha);

        Odontologo odontologoEntity = new Odontologo();
        odontologoEntity.setId(odontologoId);
        odontologoEntity.setNombre("Homero");
        odontologoEntity.setApellido("Simpson");

        Paciente pacienteEntity = new Paciente();
        pacienteEntity.setId(pacienteId);
        pacienteEntity.setNombre("Lisa");
        pacienteEntity.setApellido("Simpson");

        Turno turnoEntity = new Turno();
        turnoEntity.setOdontologo(odontologoEntity);
        turnoEntity.setPaciente(pacienteEntity);
        turnoEntity.setFecha(fecha);

        Turno turnoGuardado = new Turno();
        turnoGuardado.setId(1L);
        turnoGuardado.setOdontologo(odontologoEntity);
        turnoGuardado.setPaciente(pacienteEntity);
        turnoGuardado.setFecha(fecha);

        TurnoDTO turnoDTOGuardado = new TurnoDTO();
        turnoDTOGuardado.setId(1L);
        turnoDTOGuardado.setOdontologo(odontologoDTO);
        turnoDTOGuardado.setPaciente(pacienteDTO);
        turnoDTOGuardado.setFecha(fecha);

        when(objectMapper.convertValue(turnoDTO, Turno.class)).thenReturn(turnoEntity);
        when(pacienteService.buscarPacientePorId(pacienteId)).thenReturn(pacienteDTO);
        when(odontologoService.buscarOdontologoPorId(odontologoId)).thenReturn(odontologoDTO);
        when(objectMapper.convertValue(pacienteDTO, Paciente.class)).thenReturn(pacienteEntity);
        when(objectMapper.convertValue(odontologoDTO, Odontologo.class)).thenReturn(odontologoEntity);
        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoGuardado);
        when(objectMapper.convertValue(turnoGuardado, TurnoDTO.class)).thenReturn(turnoDTOGuardado);

        // Act
        TurnoDTO guardado = turnoService.guardarTurno(turnoDTO);

        // Assert
        Assertions.assertNotNull(guardado.getId());
        Assertions.assertEquals(fecha, guardado.getFecha());
        Assertions.assertEquals(odontologoId, guardado.getOdontologo().getId());
        Assertions.assertEquals(pacienteId, guardado.getPaciente().getId());

        // Verify
        verify(objectMapper, times(1)).convertValue(turnoDTO, Turno.class);
        verify(pacienteService, times(1)).buscarPacientePorId(pacienteId);
        verify(odontologoService, times(1)).buscarOdontologoPorId(odontologoId);
        verify(turnoRepository, times(1)).save(any(Turno.class));
        verify(objectMapper, times(1)).convertValue(turnoGuardado, TurnoDTO.class);
    }

    @Test
    @DisplayName("Listar todos los turnos")
    public void listarTurnos() {
        // Arrange
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("Homero");
        odontologo.setApellido("Simpson");

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Lisa");
        paciente.setApellido("Simpson");

        Turno turno1 = new Turno();
        turno1.setId(1L);
        turno1.setOdontologo(odontologo);
        turno1.setPaciente(paciente);
        turno1.setFecha(LocalDate.now());

        Turno turno2 = new Turno();
        turno2.setId(2L);
        turno2.setOdontologo(odontologo);
        turno2.setPaciente(paciente);
        turno2.setFecha(LocalDate.now().plusDays(1));

        List<Turno> turnos = Arrays.asList(turno1, turno2);

        TurnoDTO dto1 = new TurnoDTO();
        dto1.setId(1L);
        dto1.setFecha(LocalDate.now());

        TurnoDTO dto2 = new TurnoDTO();
        dto2.setId(2L);
        dto2.setFecha(LocalDate.now().plusDays(1));

        when(turnoRepository.findAll()).thenReturn(turnos);
        when(objectMapper.convertValue(turno1, TurnoDTO.class)).thenReturn(dto1);
        when(objectMapper.convertValue(turno2, TurnoDTO.class)).thenReturn(dto2);

        // Act
        List<TurnoDTO> lista = turnoService.listarTurnos();

        // Assert
        Assertions.assertNotNull(lista);
        Assertions.assertEquals(2, lista.size());
        Assertions.assertEquals(1L, lista.get(0).getId());
        Assertions.assertEquals(2L, lista.get(1).getId());

        // Verify
        verify(turnoRepository, times(1)).findAll();
        verify(objectMapper, times(2)).convertValue(any(Turno.class), eq(TurnoDTO.class));
    }

    @Test
    @DisplayName("Buscar turno por ID")
    public void buscarTurnoPorId() {
        // Arrange
        Long id = 1L;
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("Homero");

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Lisa");

        Turno turno = new Turno();
        turno.setId(id);
        turno.setOdontologo(odontologo);
        turno.setPaciente(paciente);
        turno.setFecha(LocalDate.now());

        TurnoDTO dto = new TurnoDTO();
        dto.setId(id);
        dto.setFecha(LocalDate.now());

        when(turnoRepository.findById(id)).thenReturn(Optional.of(turno));
        when(objectMapper.convertValue(turno, TurnoDTO.class)).thenReturn(dto);

        // Act
        TurnoDTO encontrado = turnoService.buscarTurnoPorId(id);

        // Assert
        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(id, encontrado.getId());

        // Verify
        verify(turnoRepository, times(1)).findById(id);
        verify(objectMapper, times(1)).convertValue(turno, TurnoDTO.class);
    }

    @Test
    @DisplayName("Buscar turno por ID - No encontrado")
    public void buscarTurnoPorId_NoEncontrado() {
        // Arrange
        Long id = 999L;
        when(turnoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> turnoService.buscarTurnoPorId(id)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el paciente con el ID " + id));

        // Verify
        verify(turnoRepository, times(1)).findById(id);
        verify(objectMapper, never()).convertValue(any(), eq(TurnoDTO.class));
    }

    @Test
    @DisplayName("Modificar fecha de un turno")
    public void modificarTurno() {
        // Arrange
        Long id = 1L;
        Long odontologoId = 1L;
        Long pacienteId = 1L;
        LocalDate nuevaFecha = LocalDate.now().plusDays(5);

        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(odontologoId);

        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(pacienteId);

        TurnoDTO turnoDTOActualizado = new TurnoDTO();
        turnoDTOActualizado.setId(id);
        turnoDTOActualizado.setOdontologo(odontologoDTO);
        turnoDTOActualizado.setPaciente(pacienteDTO);
        turnoDTOActualizado.setFecha(nuevaFecha);

        Odontologo odontologo = new Odontologo();
        odontologo.setId(odontologoId);

        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);

        Turno turnoExistente = new Turno();
        turnoExistente.setId(id);
        turnoExistente.setOdontologo(odontologo);
        turnoExistente.setPaciente(paciente);
        turnoExistente.setFecha(LocalDate.now());

        Turno turnoModificado = new Turno();
        turnoModificado.setId(id);
        turnoModificado.setOdontologo(odontologo);
        turnoModificado.setPaciente(paciente);
        turnoModificado.setFecha(nuevaFecha);

        TurnoDTO turnoDTOResultado = new TurnoDTO();
        turnoDTOResultado.setId(id);
        turnoDTOResultado.setFecha(nuevaFecha);

        when(turnoRepository.findById(id)).thenReturn(Optional.of(turnoExistente));
        when(odontologoRepository.findById(odontologoId)).thenReturn(Optional.of(odontologo));
        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.of(paciente));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoModificado);
        when(objectMapper.convertValue(turnoModificado, TurnoDTO.class)).thenReturn(turnoDTOResultado);

        // Act
        TurnoDTO modificado = turnoService.editarTurno(id, turnoDTOActualizado);

        // Assert
        Assertions.assertNotNull(modificado);
        Assertions.assertEquals(nuevaFecha, modificado.getFecha());

        // Verify
        verify(turnoRepository, times(1)).findById(id);
        verify(odontologoRepository, times(1)).findById(odontologoId);
        verify(pacienteRepository, times(1)).findById(pacienteId);
        verify(turnoRepository, times(1)).save(any(Turno.class));
        verify(objectMapper, times(1)).convertValue(turnoModificado, TurnoDTO.class);
    }

    @Test
    @DisplayName("Eliminar un turno existente")
    public void eliminarTurno() {
        // Arrange
        Long id = 1L;
        Turno turno = new Turno();
        turno.setId(id);
        turno.setFecha(LocalDate.now());

        when(turnoRepository.findById(id)).thenReturn(Optional.of(turno));
        doNothing().when(turnoRepository).delete(turno);

        // Act
        String mensaje = turnoService.eliminarTurnoPorId(id);

        // Assert
        Assertions.assertNotNull(mensaje);
        Assertions.assertTrue(mensaje.contains("eliminado") || mensaje.contains("Eliminado"));
        Assertions.assertTrue(mensaje.contains(String.valueOf(id)));

        // Verify
        verify(turnoRepository, times(1)).findById(id);
        verify(turnoRepository, times(1)).delete(turno);
    }

    @Test
    @DisplayName("Eliminar turno - No encontrado")
    public void eliminarTurno_NoEncontrado() {
        // Arrange
        Long id = 999L;
        when(turnoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = Assertions.assertThrows(
            NotFoundException.class,
            () -> turnoService.eliminarTurnoPorId(id)
        );

        Assertions.assertTrue(exception.getMessage().contains("No se encontró el turno con ID " + id));

        // Verify
        verify(turnoRepository, times(1)).findById(id);
        verify(turnoRepository, never()).delete(any(Turno.class));
    }
}
