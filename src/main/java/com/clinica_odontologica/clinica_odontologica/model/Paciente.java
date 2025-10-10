package com.clinica_odontologica.clinica_odontologica.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
	private int id;
	private String nombre;
	private String apellido;
	private int numeroContacto;
	private LocalDate fechaIngreso;
	private String domicilio;
	private String email;

	public Paciente(String nombre, String apellido, int numeroContacto, String domicilio, String email,
			LocalDate fechaIngreso) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.numeroContacto = numeroContacto;
		this.domicilio = domicilio;
		this.fechaIngreso = fechaIngreso;
		this.email = email;
	}
}
