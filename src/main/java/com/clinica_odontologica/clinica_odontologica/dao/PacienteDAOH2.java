package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.clinica_odontologica.clinica_odontologica.model.Domicilio;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

public class PacienteDAOH2 implements IDAO<Paciente> {
	private IDAO<Paciente> pacienteDAO;
	private static final String SQL_SELECT_ONE = "SELECT * FROM PACIENTES WHERE ID =?";

	@Override
	public Paciente guardar(Paciente paciente) {
		return null;
	}

	@Override
	public Paciente buscar(Integer id) {
		Connection connection = null;
		Paciente paciente = null;
		Domicilio domicilio = null;

		try {
			connection = BD.getConnection();
			Statement statement = connection.createStatement();
			PreparedStatement ps_select_one = connection.prepareStatement(SQL_SELECT_ONE);
			ps_select_one.setInt(1, id);
			ResultSet resultSet = ps_select_one.executeQuery();
			DomicilioDAOH2 domicilioDAOH2 = new DomicilioDAOH2();

			while (resultSet.next()) {
				domicilio = domicilioDAOH2.buscar(resultSet.getInt(6));
				paciente = new Paciente(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getDate(5).toLocalDate(), resultSet.getString(domicilio.getId()),
						resultSet.getString(7));
			}
		} catch (Exception e) {
			System.out.println("ERROR AL BUSCAR PACIENTE: " + e.getMessage());
		}

		return paciente;
	}

	@Override
	public List<Paciente> buscarTodos() {
		return List.of();
	}

	@Override
	public void eliminar(Paciente paciente) {

	}

	@Override
	public void modificar(Paciente paciente) {

	}
}
