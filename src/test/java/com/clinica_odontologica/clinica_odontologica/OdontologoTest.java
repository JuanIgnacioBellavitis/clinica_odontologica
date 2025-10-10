package com.clinica_odontologica.clinica_odontologica;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.clinica_odontologica.clinica_odontologica.dao.BD;
import com.clinica_odontologica.clinica_odontologica.dao.OdontologoDAOH2;
import com.clinica_odontologica.clinica_odontologica.dao.PacienteDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Odontologo;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;

public class OdontologoTest {
	@Test
	public void buscarPaciente() {
		BD.crearTablas();
		OdontologoService odontologoService = new OdontologoService(new OdontologoDAOH2());

		//
		Odontologo odontologo = odontologoService.buscar(2);
		System.out.println("Datos encontrados: " + odontologo);

		// ENTONCES
		Assertions.assertTrue(odontologo != null);
	}
}
