package com.clinica_odontologica.clinica_odontologica.model;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domicilio {
	private int id;
	private String calle;
	private int numero;
	private String localidad;
	private String provincia;

	public Domicilio(String calle, int numero, String localidad, String provincia) {
		this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
	}
}
