package com.clinica_odontologica.clinica_odontologica.repository;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long>{
    Optional<Odontologo> findByNombre(String name);
}
