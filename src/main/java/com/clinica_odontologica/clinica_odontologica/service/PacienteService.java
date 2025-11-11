package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.exceptions.BadRequestException;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PacienteService implements IPacienteService {

	private final PacienteRepository pacienteRepository;
	private final ObjectMapper mapper;

	@Autowired
	public PacienteService(PacienteRepository pacienteRepository, ObjectMapper objectMapper) {
		this.pacienteRepository = pacienteRepository;
		this.mapper = objectMapper;
	}

	@Override
	public PacienteDTO guardarPaciente(PacienteDTO pacienteDTO) {
		Optional<Paciente> existente = pacienteRepository.findByEmail(pacienteDTO.getEmail());

		if (existente.isPresent()) {
			throw new BadRequestException("Ya existe un paciente registrado con el email " + pacienteDTO.getEmail());
		}

		Paciente paciente = pacienteDTOAPaciente(pacienteDTO);
		Paciente pacienteGuardado = pacienteRepository.save(paciente);

		return pacienteAPacienteDTO(pacienteGuardado);
	}

	@Override
	public PacienteDTO buscarPacientePorId(Long id) {
		Paciente paciente = pacienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se encontró el paciente con el ID " + id));

		return pacienteAPacienteDTO(paciente);
	}

	@Override
	public List<PacienteDTO> listarPacientes() {
		return pacienteRepository.findAll().stream().map(this::pacienteAPacienteDTO).collect(Collectors.toList());
	}

	@Override
	public PacienteDTO editarPaciente(Long id, PacienteDTO pacienteDTO) {
		Paciente pacienteExistente = pacienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se encontró el paciente con ID " + pacienteDTO.getId()));

		pacienteExistente.setNombre(pacienteDTO.getNombre());
		pacienteExistente.setApellido(pacienteDTO.getApellido());
		pacienteExistente.setNumeroContacto(pacienteDTO.getNumeroContacto());
		pacienteExistente.setFechaIngreso(pacienteDTO.getFechaIngreso());
		pacienteExistente.setEmail(pacienteDTO.getEmail());
		pacienteExistente.setDomicilio(pacienteDTO.getDomicilio());

		Paciente pacienteActualizado = pacienteRepository.save(pacienteExistente);

		return pacienteAPacienteDTO(pacienteActualizado);
	}

	@Override
	public String eliminarPaciente(Long id) {
		Paciente paciente = pacienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se encontró el paciente con ID " + id));
		pacienteRepository.delete(paciente);
		return "El paciente " + id + " ha sido eliminado correctamente.";
	}

	@Override
	public PacienteDTO buscarPacientePorEmail(String email) {
		Paciente paciente = pacienteRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("No se encontro el paciente con el email " + email));
		return mapper.convertValue(paciente, PacienteDTO.class);
	}

	private PacienteDTO pacienteAPacienteDTO(Paciente paciente) {
		return mapper.convertValue(paciente, PacienteDTO.class);
	}

	private Paciente pacienteDTOAPaciente(PacienteDTO pacienteDTO) {
		return mapper.convertValue(pacienteDTO, Paciente.class);
	}
}
