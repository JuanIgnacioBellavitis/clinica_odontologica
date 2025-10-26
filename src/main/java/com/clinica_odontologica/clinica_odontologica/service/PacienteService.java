package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.entity.Turno;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {

    private final PacienteRepository pacienteRepository;
    private final ObjectMapper mapper;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, ObjectMapper objectMapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = objectMapper;
    }

    public PacienteDTO guardarPaciente(PacienteDTO pacienteDTO){
        Paciente paciente = PacienteDTOAPaciente(pacienteDTO);

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        return pacienteAPacienteDTO(pacienteGuardado);
    }

    @Override
    public PacienteDTO buscarPacientePorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el paciente con el ID " + id));

        return mapper.convertValue(paciente, PacienteDTO.class);
    }

    @Override
    public List<PacienteDTO> listarPacientes() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::pacienteAPacienteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteDTO editarPaciente(PacienteDTO pacienteDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> eliminarPaciente(PacienteDTO pacienteDTO) {
        return null;
    }

    @Override
    public PacienteDTO buscarPacientePorEmail(String email) {
        return null;
    }


    private PacienteDTO pacienteAPacienteDTO(Paciente paciente) {
        return mapper.convertValue(paciente, PacienteDTO.class);
    }

    private Paciente PacienteDTOAPaciente(PacienteDTO pacienteDTO) {
        return mapper.convertValue(pacienteDTO, Paciente.class);
    }
}
