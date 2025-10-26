package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPacienteService {
    PacienteDTO guardarPaciente(PacienteDTO pacienteDTO);
    PacienteDTO buscarPacientePorId(Long id);
    List<PacienteDTO> listarPacientes();
    PacienteDTO editarPaciente(Long id, PacienteDTO pacienteDTO);
    String eliminarPaciente(PacienteDTO pacienteDTO);
    PacienteDTO buscarPacientePorEmail(String email);
}
