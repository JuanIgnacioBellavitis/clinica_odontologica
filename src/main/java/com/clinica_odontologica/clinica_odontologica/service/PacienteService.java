package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

import java.util.List;

public class PacienteService implements ISERVICE<Paciente>{
    private IDAO<Paciente> pacienteDAO;

    public PacienteService(IDAO<Paciente> pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    // GUARDAR PACIENTE
    public Paciente guardar(Paciente paciente) {
        return pacienteDAO.guardar(paciente);
    }

    // LISTADO TODOS LOS PACIENTES
    public List<Paciente> buscarTodos(){
        return pacienteDAO.buscarTodos();
    }

    // BUSCAR PACIENTE POR ID
    public Paciente buscar(Integer id){
        return pacienteDAO.buscar(id);
    }

    @Override
    public Paciente buscarPorNombre(String parametro) {
        return pacienteDAO.buscarPorString(parametro);
    }

    // ELIMINAR PACIENTE
    public List<Paciente> eliminar(Integer id){
         pacienteDAO.eliminar(id);
         return pacienteDAO.buscarTodos();
    }

    // MODIFICAR PACIENTE
    public Paciente modificar(Paciente paciente){
        return  pacienteDAO.modificar(paciente);
    }
}
