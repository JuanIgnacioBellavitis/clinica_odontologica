package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.clinica_odontologica.clinica_odontologica.model.Domicilio;

public class DomicilioDAOH2 implements IDAO<Domicilio> {
	private static final String SQL_DOMICILIO = "SELECT * FROM DOMICILIOS WHERE ID=?";
    private static final String SQL_INSERT =
            "INSERT INTO DOMICILIOS (CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_DOMICILIO =
            "UPDATE DOMICILIOS SET CALLE=?, NUMERO=?, LOCALIDAD=?, PROVINCIA=? WHERE ID=?";

    @Override
    public Domicilio guardar(Domicilio domicilio) {
        Domicilio domicilioGuardado = null;

        try (Connection conn = BD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                domicilioGuardado = new Domicilio(
                        rs.getInt(1),
                        domicilio.getCalle(),
                        domicilio.getNumero(),
                        domicilio.getLocalidad(),
                        domicilio.getProvincia()
                );
            }
        } catch (Exception e) {
            System.out.println("ERROR AL GUARDAR DOMICILIO: " + e.getMessage());
        }

        return domicilioGuardado;
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
    public Domicilio modificar(Domicilio domicilio) {
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_DOMICILIO);
            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.setInt(5, domicilio.getId());
            ps.executeUpdate();

            System.out.println("Domicilio modificado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al modificar domicilio: " + e.getMessage());
        }

        return domicilio;
    }


	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Domicilio buscarPorString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
