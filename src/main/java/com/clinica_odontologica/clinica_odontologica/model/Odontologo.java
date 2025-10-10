package com.clinica_odontologica.clinica_odontologica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class Odontologo {
	private int id;
	private String nombre;
	private String apellido;
	private Integer matricula;

	public Odontologo(String nombre, String apellido, Integer Matricula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = matricula;

	}

}
