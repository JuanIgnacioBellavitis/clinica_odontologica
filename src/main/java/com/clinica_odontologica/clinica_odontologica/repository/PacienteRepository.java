package com.clinica_odontologica.clinica_odontologica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica_odontologica.clinica_odontologica.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	Optional<Paciente> findByEmail(String email);
}
