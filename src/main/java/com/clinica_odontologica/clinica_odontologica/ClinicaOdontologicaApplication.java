package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dao.BD;

import com.clinica_odontologica.clinica_odontologica.dao.PacienteDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ClinicaOdontologicaApplication {

	public static void main(String[] args) {

        BD.crearTablas();
        SpringApplication.run(ClinicaOdontologicaApplication.class, args);
        PacienteDAOH2 pacienteDAOH2 = new PacienteDAOH2();
        PacienteService pacienteService = new PacienteService(pacienteDAOH2);
        Paciente pacienteGuardado = null;

        Paciente paciente = new Paciente("Bart", "Simpson", 11223344,
                "1", "bart@disney.com", LocalDate.of(2025, 10, 9));

        pacienteGuardado = pacienteService.guardarPaciente(paciente);

        System.out.println("Paciente guardado: " + pacienteGuardado);
    }

}
