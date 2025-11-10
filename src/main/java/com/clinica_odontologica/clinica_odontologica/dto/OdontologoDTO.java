package com.clinica_odontologica.clinica_odontologica.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OdontologoDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private Integer matricula;
}
