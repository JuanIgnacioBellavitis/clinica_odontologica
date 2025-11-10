package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;

public interface IPacienteService {
	PacienteDTO guardarPaciente(PacienteDTO pacienteDTO);

	PacienteDTO buscarPacientePorId(Long id);

	List<PacienteDTO> listarPacientes();

	PacienteDTO editarPaciente(Long id, PacienteDTO pacienteDTO);

	String eliminarPaciente(PacienteDTO pacienteDTO);

	PacienteDTO buscarPacientePorEmail(String email);
}
