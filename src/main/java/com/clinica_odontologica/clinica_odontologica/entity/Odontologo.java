package com.clinica_odontologica.clinica_odontologica.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column
	private String nombre;

    @Column
	private String apellido;

    @Column(unique = true, nullable = false)
    private Integer matricula;

	public Odontologo(String nombre, String apellido, Integer Matricula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = Matricula;
	}

}
