package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;

import com.clinica_odontologica.clinica_odontologica.dao.IDAO;
import com.clinica_odontologica.clinica_odontologica.model.Odontologo;

public class OdontologoService implements ISERVICE<Odontologo> {

	private IDAO<Odontologo> odontologoDAO;

	public OdontologoService(IDAO<Odontologo> odontologoDAO) {
		this.odontologoDAO = odontologoDAO;
	}

	@Override
	public Odontologo guardar(Odontologo odontologo) {
		return odontologoDAO.guardar(odontologo);
	}

	@Override
	public Odontologo buscar(Integer id) {
		return odontologoDAO.buscar(id);
	}

	@Override
	public List<Odontologo> buscarTodos() {
		return odontologoDAO.buscarTodos();
	}

	@Override
	public List<Odontologo> eliminar(Integer id) {
		odontologoDAO.eliminar(id);
		List<Odontologo> odontologoRestantes = odontologoDAO.buscarTodos();

		return odontologoRestantes;

	}

	@Override
	public Odontologo modificar(Odontologo odontologo) {
		return odontologoDAO.modificar(odontologo);

	}

}
