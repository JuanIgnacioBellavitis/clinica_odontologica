package com.clinica_odontologica.clinica_odontologica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica_odontologica.clinica_odontologica.entity.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
}
