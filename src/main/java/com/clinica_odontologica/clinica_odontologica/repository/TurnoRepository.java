package com.clinica_odontologica.clinica_odontologica.repository;
import com.clinica_odontologica.clinica_odontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{
}
