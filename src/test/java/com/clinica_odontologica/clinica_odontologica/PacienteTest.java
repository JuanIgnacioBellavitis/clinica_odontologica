package com.clinica_odontologica.clinica_odontologica;

import com.clinica_odontologica.clinica_odontologica.dao.BD;
import com.clinica_odontologica.clinica_odontologica.dao.PacienteDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PacienteTest {

    @Test
    public void buscarPaciente(){
        BD.crearTablas();
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());

        // 
        Paciente paciente = pacienteService.buscarPacientePorId(2);
        System.out.println("Datos encontreados: " + paciente);

        // ENTONCES
        Assertions.assertTrue(paciente != null);
    }
}
