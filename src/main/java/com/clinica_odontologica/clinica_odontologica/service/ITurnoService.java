package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;

public interface ITurnoService {
    TurnoDTO guardarTurno(TurnoDTO turnoDTO);
    TurnoDTO buscarTurnoPorId(Long id);
}
