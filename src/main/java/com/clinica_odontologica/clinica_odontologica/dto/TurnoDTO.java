package com.clinica_odontologica.clinica_odontologica.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TurnoDTO {
	private Long id;
	private LocalDate fecha;
	private PacienteDTO paciente;
	private OdontologoDTO odontologo;
}
