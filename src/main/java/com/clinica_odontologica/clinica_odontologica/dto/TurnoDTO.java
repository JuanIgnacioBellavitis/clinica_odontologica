package com.clinica_odontologica.clinica_odontologica.dto;

import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class TurnoDTO {
    private Long id;
    private LocalDate fecha;

    private PacienteDTO paciente;

    private OdontologoDTO odontologo;
}
