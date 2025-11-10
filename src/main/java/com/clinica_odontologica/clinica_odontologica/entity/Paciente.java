package com.clinica_odontologica.clinica_odontologica.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
	private String apellido;

	@Column
	private int numeroContacto;

	@Column
	private LocalDate fechaIngreso;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "domicilio_id", referencedColumnName = "id")
	private Domicilio domicilio;

	@Column(unique = true)
	private String email;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turno> turnos = new HashSet<Turno>();

	public Paciente(String nombre, String apellido, int numeroContacto, Domicilio domicilio, String email,
			LocalDate fechaIngreso) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.numeroContacto = numeroContacto;
		this.domicilio = domicilio;
		this.fechaIngreso = fechaIngreso;
		this.email = email;
	}
}
