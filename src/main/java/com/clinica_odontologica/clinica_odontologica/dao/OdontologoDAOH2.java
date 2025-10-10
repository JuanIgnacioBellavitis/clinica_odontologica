package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.clinica_odontologica.clinica_odontologica.model.Odontologo;

public class OdontologoDAOH2 implements IDAO<Odontologo> {
	private IDAO<Odontologo> odontologoDAO;
	private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS (NOMBRE, APELLIDO, MATRICULA) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID =?";
	private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
	private static final String SQL_DELETE = "DELETE FROM ODONTOLOGOS WHERE ID=?";
	private static final String SQL_UPDATE = "UPDATE ODONTOLOGOS SET NOMBRE=?, APELLIDO=?, MATRICULA=? WHERE ID=?";

	@Override
	public Odontologo guardar(Odontologo odontologo) {
		Odontologo odontologoGuardado = null;
		Connection connection = null;

		try {
			connection = BD.getConnection();
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			System.out.println("LLEGA ODONTOLOGO: " + odontologo);

			ps.setString(1, odontologo.getNombre());
			ps.setString(2, odontologo.getApellido());
			ps.setInt(3, odontologo.getMatricula());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				odontologoGuardado = new Odontologo(rs.getInt(1), odontologo.getNombre(), odontologo.getApellido(),
						odontologo.getMatricula());
			}

			return odontologoGuardado;
		} catch (Exception ex) {
			System.out.println("Error al intentar guardar Paciente: " + ex.getMessage());
		}

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
		List<Odontologo> listarOdontologo = new ArrayList<>();
		Connection connection = null;

		try {
			connection = BD.getConnection();
			PreparedStatement ps_select_all = connection.prepareStatement(SQL_SELECT_ALL);
			ResultSet resultSet = ps_select_all.executeQuery();

			while (resultSet.next()) {
				listarOdontologo.add(mapearOdontologo(resultSet));
			}
		} catch (Exception e) {
			System.out.println("ERROR AL BUSCAR ODONTOLOGO: " + e.getMessage());
		}

		return listarOdontologo;
	}

	@Override
	public void eliminar(Integer id) {
		Connection connection = null;

		try {
			connection = BD.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

			System.out.println("Odontologo eliminado: " + id);
		} catch (Exception e) {
			System.err.println("ERROR AL ELIMINAR ODONTOLOGO: " + e.getMessage());
		}
	}

	@Override
	public Odontologo modificar(Odontologo odontologo) {
		Connection connection = null;

		try {
			connection = BD.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

			preparedStatement.setString(1, odontologo.getNombre());
			preparedStatement.setString(2, odontologo.getApellido());
			preparedStatement.setInt(3, odontologo.getMatricula());
			preparedStatement.setInt(4, odontologo.getId());

			preparedStatement.executeUpdate();

			System.out.println("Odontologo modificado: " + odontologo.getId());

			return odontologo;
		} catch (Exception e) {
			System.out.println("ERROR AL MODIFICAR PACIENTE: " + e.getMessage());
		}

		return null;
	}

	private Odontologo mapearOdontologo(ResultSet rs) throws SQLException {
		return new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
	}

}
