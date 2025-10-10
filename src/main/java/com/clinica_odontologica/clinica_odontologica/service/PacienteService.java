package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

import java.util.List;

public class PacienteService {
    private IDAO<Paciente> pacienteDAO;

    public PacienteService(IDAO<Paciente> pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    // GUARDAR PACIENTE
    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteDAO.guardar(paciente);
    }

    // LISTADO TODOS LOS PACIENTES
    public List<Paciente> buscarPacientes(){
        return pacienteDAO.buscarTodos();
    }

    // BUSCAR PACIENTE POR ID
    public Paciente buscarPacientePorId(Integer id){
        return pacienteDAO.buscar(id);
    }

    // ELIMINAR PACIENTE
    public List<Paciente> eliminarPaciente(Integer id){
         pacienteDAO.eliminar(id);
         return pacienteDAO.buscarTodos();
    }

    // MODIFICAR PACIENTE
    public Paciente modificarPaciente(Paciente paciente){
        return  pacienteDAO.modificar(paciente);
    }
}
