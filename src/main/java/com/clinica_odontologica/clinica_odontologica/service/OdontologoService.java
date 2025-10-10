package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;

import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.dao.OdontologoDAOH2;
import com.clinica_odontologica.clinica_odontologica.model.Odontologo;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

public class OdontologoService implements ISERVICE<Odontologo> {
	
	private IDAO<Odontologo> odontologoDAO;

	public OdontologoService(IDAO<Odontologo> odontologoDAO) {
		this.odontologoDAO = odontologoDAO;
	}

	@Override
	public Odontologo guardar(Odontologo t) {

		return null;
	}

	@Override
	public Odontologo buscar(Integer id) {
		return odontologoDAO.buscar(id);
	}

	@Override
	public List<Odontologo> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Odontologo t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificar(Odontologo t) {
		// TODO Auto-generated method stub

	}

}
