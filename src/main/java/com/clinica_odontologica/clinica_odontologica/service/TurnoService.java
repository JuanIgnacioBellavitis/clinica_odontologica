package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.entity.Turno;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private final TurnoRepository turnoRepository;
    private final ObjectMapper mapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository,
                        PacienteService pacienteService,
                        OdontologoService odontologoService,
                        ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.mapper = mapper;
    }

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

    @Override
    public TurnoDTO buscarTurnoPorId(Long id) {
        return null;
    }

    private TurnoDTO turnoATurnoDTO(Turno turno) {
        return mapper.convertValue(turno, TurnoDTO.class);
    }

    private Turno turnoDTOATurno(TurnoDTO turnoDTO) {
        return mapper.convertValue(turnoDTO, Turno.class);
    }
}

