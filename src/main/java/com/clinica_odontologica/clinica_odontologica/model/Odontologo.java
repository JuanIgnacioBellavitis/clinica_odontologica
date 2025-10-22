package com.clinica_odontologica.clinica_odontologica.model;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Odontologo {
	private int id;
	private String nombre;
	private String apellido;
	private Integer matricula;

	public Odontologo(String nombre, String apellido, Integer Matricula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = Matricula;
	}

}
