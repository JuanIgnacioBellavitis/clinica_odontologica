package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dao.BD;

import com.clinica_odontologica.clinica_odontologica.dao.PacienteDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ClinicaOdontologicaApplication {

	public static void main(String[] args) {

        BD.crearTablas();
        SpringApplication.run(ClinicaOdontologicaApplication.class, args);
        PacienteDAOH2 pacienteDAOH2 = new PacienteDAOH2();
        PacienteService pacienteService = new PacienteService(pacienteDAOH2);
        Paciente pacienteGuardado = null;
        Paciente pacienteEncontrado = null;
        Paciente pacienteModificado = null;
        List<Paciente> listadoPacientes = null;

        Paciente paciente = new Paciente("Bart", "Simpson", 11223344,
                "1", "bartdisney.com", LocalDate.of(2025, 10, 9));

        pacienteGuardado = pacienteService.guardarPaciente(paciente);
        pacienteEncontrado = pacienteService.buscarPacientePorId(2);
        listadoPacientes = pacienteService.buscarPacientes();

        pacienteModificado =  pacienteService.buscarPacientePorId(1);
        pacienteModificado.setNombre("Homer Jackson");
        pacienteModificado.setApellido("Sim");
        pacienteModificado.setEmail("homerJSimpson@mail.com");
        pacienteModificado.setFechaIngreso(LocalDate.now());

        System.out.println("----------------------------------------------------");
        System.out.println("Paciente guardado: " + pacienteGuardado);
        System.out.println("----------------------------------------------------");
        System.out.println("Paciente encontrado con ID 2: " + pacienteEncontrado);
        System.out.println("----------------------------------------------------");
        System.out.println("Listado de Pacientes: ");
        System.out.println(listadoPacientes);
        System.out.println("----------------------------------------------------");
        System.out.println("Eliminar paciente: 2");
        List<Paciente> nuevoListado = pacienteService.eliminarPaciente(2);
        System.out.println(nuevoListado);
        System.out.println("----------------------------------------------------");
        System.out.println("Modificado paciente: 1");
        System.out.println(pacienteService.modificarPaciente(pacienteModificado));
    }

}
