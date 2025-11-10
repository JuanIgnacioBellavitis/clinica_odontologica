package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.entity.Turno;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.OdontologoRepository;
import com.clinica_odontologica.clinica_odontologica.repository.PacienteRepository;
import com.clinica_odontologica.clinica_odontologica.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TurnoService implements ITurnoService {

	private final TurnoRepository turnoRepository;
	private final OdontologoRepository odontologoRepository;
	private final PacienteRepository pacienteRepository;
	private final ObjectMapper mapper;
	private final PacienteService pacienteService;
	private final OdontologoService odontologoService;

	@Autowired
	public TurnoService(TurnoRepository turnoRepository, OdontologoRepository odontologoRepository,
			PacienteRepository pacienteRepository, PacienteService pacienteService, OdontologoService odontologoService,
			ObjectMapper mapper) {
		this.turnoRepository = turnoRepository;
		this.odontologoRepository = odontologoRepository;
		this.pacienteRepository = pacienteRepository;

		this.pacienteService = pacienteService;
		this.odontologoService = odontologoService;
		this.mapper = mapper;
	}

	@Override
	public TurnoDTO guardarTurno(TurnoDTO turnoDTO) {
		Turno turno = turnoDTOATurno(turnoDTO);

		PacienteDTO pacienteDTO = pacienteService.buscarPacientePorId(turnoDTO.getPaciente().getId());
		OdontologoDTO odontologoDTO = odontologoService.buscarOdontologoPorId(turnoDTO.getOdontologo().getId());

		Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
		Odontologo odontologo = mapper.convertValue(odontologoDTO, Odontologo.class);

		turno.setPaciente(paciente);
		turno.setOdontologo(odontologo);

		Turno turnoGuardado = turnoRepository.save(turno);

		return turnoATurnoDTO(turnoGuardado);
	}

	public List<TurnoDTO> listarTurnos() {
		return turnoRepository.findAll().stream().map(this::turnoATurnoDTO).collect(Collectors.toList());
	}

	@Override
	public TurnoDTO buscarTurnoPorId(Long id) {
		Turno turno = turnoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se encontró el paciente con el ID " + id));

		return turnoATurnoDTO(turno);
	}

	private TurnoDTO turnoATurnoDTO(Turno turno) {
		return mapper.convertValue(turno, TurnoDTO.class);
	}

	private Turno turnoDTOATurno(TurnoDTO turnoDTO) {
		return mapper.convertValue(turnoDTO, Turno.class);
	}

	@Override
	public String eliminarTurnoPorId(Long id) {
		Turno turno = turnoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se encontró el turno con ID " + id));
		turnoRepository.delete(turno);
		return "El turno " + id + " ha sido eliminado correctamente.";
	}

	@Override
	public TurnoDTO editarTurno(Long id, TurnoDTO turnoDto) {
		Turno turnoExistente = turnoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No se encontró el turno con ID " + id));

		Odontologo odontologo = odontologoRepository.findById(turnoDto.getOdontologo().getId()).orElseThrow(
				() -> new NotFoundException("No se encontró el odontólogo con ID " + turnoDto.getOdontologo().getId()));

		Paciente paciente = pacienteRepository.findById(turnoDto.getPaciente().getId()).orElseThrow(
				() -> new NotFoundException("No se encontró el paciente con ID " + turnoDto.getPaciente().getId()));

		turnoExistente.setOdontologo(odontologo);
		turnoExistente.setPaciente(paciente);
		turnoExistente.setFecha(turnoDto.getFecha());

		Turno turnoActualizado = turnoRepository.save(turnoExistente);
		return turnoATurnoDTO(turnoActualizado);
	}
}
