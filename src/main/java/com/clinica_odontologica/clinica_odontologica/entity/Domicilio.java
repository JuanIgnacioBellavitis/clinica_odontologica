package com.clinica_odontologica.clinica_odontologica.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "domicilio")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column
	private String calle;

    @Column
	private int numero;

    @Column
	private String localidad;

    @Column
	private String provincia;

	public Domicilio(String calle, int numero, String localidad, String provincia) {
		this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
	}
}
