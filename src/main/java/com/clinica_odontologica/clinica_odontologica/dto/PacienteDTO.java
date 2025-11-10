package com.clinica_odontologica.clinica_odontologica.dto;

import java.time.LocalDate;

import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private int numeroContacto;
	private LocalDate fechaIngreso;
	private Domicilio domicilio;
	private String email;
}
