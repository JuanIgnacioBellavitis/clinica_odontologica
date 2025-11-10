package com.clinica_odontologica.clinica_odontologica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
	Optional<Odontologo> findByNombre(String name);
}
