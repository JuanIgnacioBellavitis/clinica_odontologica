package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

import java.util.List;

public class PacienteService {
    private IDAO<Paciente> pacienteDAO;

    public PacienteService(IDAO<Paciente> pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteDAO.guardar(paciente);
    }

    public List<Paciente> buscarPacientes(){
        return pacienteDAO.buscarTodos();
    }

    public Paciente buscarPacientePorId(Integer id){
        return pacienteDAO.bucar(id);
    }

    public List<Paciente> eliminarPaciente(Integer id){
        pacienteDAO.eliminar(id);
        List<Paciente> pacientesRestantes = pacienteDAO.buscarTodos();

        return pacientesRestantes;
    }

    public Paciente modificarPaciente(Paciente paciente){
        return pacienteDAO.modificar(paciente);
    }
}
