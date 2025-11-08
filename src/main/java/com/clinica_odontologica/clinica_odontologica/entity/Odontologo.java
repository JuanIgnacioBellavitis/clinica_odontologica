package com.clinica_odontologica.clinica_odontologica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<Turno>();

	public Odontologo(String nombre, String apellido, Integer Matricula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.matricula = Matricula;
	}

}
