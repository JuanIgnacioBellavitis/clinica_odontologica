package com.clinica_odontologica.clinica_odontologica;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.clinica_odontologica.clinica_odontologica.dao.BD;
import com.clinica_odontologica.clinica_odontologica.dao.OdontologoDAOH2;
import com.clinica_odontologica.clinica_odontologica.dao.PacienteDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Odontologo;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;

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

		Paciente paciente = new Paciente("Bart", "Simpson", 11223344, "1", "bart@disney.com",
				LocalDate.of(2025, 10, 9));

		pacienteGuardado = pacienteService.guardarPaciente(paciente);
		pacienteEncontrado = pacienteService.buscarPacientePorId(2);
		listadoPacientes = pacienteService.buscarPacientes();

		pacienteModificado = pacienteService.buscarPacientePorId(1);
		pacienteModificado.setNombre("Homer Jackson");
		pacienteModificado.setApellido("Sim");
		pacienteModificado.setEmail("homerJSimpson@mail.com");
		pacienteModificado.setFechaIngreso(LocalDate.now());
		System.out.println("---------------INFORMACION DE PACIENTES--------------------------");
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

		OdontologoDAOH2 odontologoDAOH2 = new OdontologoDAOH2();
		OdontologoService odontologoService = new OdontologoService(odontologoDAOH2);

		Odontologo odontologoGuardado = null;
		Odontologo odontologoEncontrado = null;
		Odontologo odontologoModificado = null;
		List<Odontologo> listadoOdontologos = null;

		Odontologo odontologo = new Odontologo("Bob", "Patiño", 11223344);

		odontologoGuardado = odontologoService.guardar(odontologo);
		odontologoEncontrado = odontologoService.buscar(2);
		listadoOdontologos = odontologoService.buscarTodos();

		odontologoModificado = odontologoService.buscar(1);
		odontologoModificado.setNombre("Bobina");
		odontologoModificado.setApellido("Ruxon");
		odontologoModificado.setMatricula(123123);
		System.out.println("---------------INFORMACION DE ODONTOLOGOS--------------------------");
		System.out.println("----------------------------------------------------");
		System.out.println("Odontologo guardado: " + odontologoGuardado);
		System.out.println("----------------------------------------------------");
		System.out.println("Odontologo encontrado con ID 2: " + odontologoEncontrado);
		System.out.println("----------------------------------------------------");
		System.out.println("Listado de Odontologos: ");
		System.out.println(listadoOdontologos);
		System.out.println("----------------------------------------------------");
		System.out.println("Eliminar paciente: 2");
		List<Odontologo> nuevoListadoOdontologo = odontologoService.eliminar(2);
		System.out.println(nuevoListadoOdontologo);
		System.out.println("----------------------------------------------------");
		System.out.println("Modificado odontologo: 1");
		System.out.println(odontologoService.modificar(odontologoModificado));
	}

}
