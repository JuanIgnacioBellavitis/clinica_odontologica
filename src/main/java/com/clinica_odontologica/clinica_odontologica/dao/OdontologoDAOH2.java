package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.clinica_odontologica.clinica_odontologica.model.Odontologo;

public class OdontologoDAOH2 implements IDAO<Odontologo> {
	private IDAO<Odontologo> odontologoDAO;
	private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID =?";

	@Override
	public Odontologo guardar(Odontologo t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Odontologo buscar(Integer id) {
		Connection connection = null;
		Odontologo odontologo = null;

		try {
			connection = BD.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps_select_one = connection.prepareStatement(SQL_SELECT_ONE);
			ps_select_one.setInt(1, id);
			ResultSet resultSet = ps_select_one.executeQuery();

			while (resultSet.next()) {
				odontologo = new Odontologo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4));
			}
		} catch (Exception e) {
			System.out.println("ERROR AL BUSCAR ODONTOLOGO: " + e.getMessage());
		}

		return odontologo;
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
