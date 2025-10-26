package com.clinica_odontologica.clinica_odontologica.dto;

import com.clinica_odontologica.clinica_odontologica.entity.Domicilio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
