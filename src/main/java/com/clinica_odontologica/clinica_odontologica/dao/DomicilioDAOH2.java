package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.clinica_odontologica.clinica_odontologica.model.Domicilio;

public class DomicilioDAOH2 implements IDAO<Domicilio> {
	private static final String SQL_DOMICILIO = "SELECT * FROM DOMICILIOS WHERE ID=?";

	@Override
	public Domicilio guardar(Domicilio domicilio) {
		return null;
	}

	@Override
	public Domicilio buscar(Integer id) {
		Connection conn = null;
		Domicilio domicilio = null;
		try {
			conn = BD.getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_DOMICILIO);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				domicilio = new Domicilio(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5));
			}

		} catch (Exception e) {
			e.getMessage();
		}

		System.out.println("Domicilio encontrado: " + domicilio);
		return domicilio;
	}

	@Override
	public List<Domicilio> buscarTodos() {
		return List.of();
	}

    @Override
    public void eliminar(Integer id) {

	}

    @Override
    public Domicilio modificar(Domicilio domicilio) {
        return domicilio;
    }
}
