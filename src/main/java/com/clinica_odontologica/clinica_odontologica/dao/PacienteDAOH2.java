package com.clinica_odontologica.clinica_odontologica.dao;

import com.clinica_odontologica.clinica_odontologica.model.Domicilio;
import com.clinica_odontologica.clinica_odontologica.model.Odontologo;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements IDAO<Paciente> {
    private IDAO<Paciente> pacienteDAO;
    private IDAO<Domicilio> domicilioDAO;

    private static final String SQL_INSERT =
            "INSERT INTO PACIENTES (NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ONE =
            "SELECT * FROM PACIENTES WHERE ID = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM PACIENTES";

    private static final String SQL_UPDATE =
            "UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, NUMEROCONTACTO=?, FECHAINGRESO=?, DOMICILIO_ID=?, EMAIL=? WHERE ID=?";

    private static final String SQL_DELETE =
            "DELETE FROM PACIENTES WHERE ID=?";

    private static final String SQL_SELECT_NOMBRE =
            "SELECT * FROM PACIENTES WHERE NOMBRE =?";


    @Override
    public Paciente guardar(Paciente paciente) {
        Paciente pacienteGuardado = null;
        Domicilio domicilio = null;
        Connection connection =  null;

        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            System.out.println("LLEGA PACIENTE: " + paciente);

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getNumeroContacto());
            ps.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps.setString(5, paciente.getDomicilio());
            ps.setString(6, paciente.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                pacienteGuardado = new Paciente(
                        rs.getInt(1),
                        paciente.getNombre(),
                        paciente.getApellido(),
                        paciente.getNumeroContacto(),
                        paciente.getFechaIngreso(),
                        paciente.getDomicilio(),
                        paciente.getEmail()
                );
            }

            return pacienteGuardado;
        } catch(Exception ex) {
            System.out.println("Error al intentar guardar Paciente: " + ex.getMessage());
        }

        return null;
    }

	@Override
	public Paciente buscar(Integer id) {
		Connection connection = null;
		Paciente paciente = null;
		Domicilio domicilio = null;

        try {
            connection = BD.getConnection();
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
        List<Paciente> listarPacientes = new ArrayList<>();
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement ps_select_all = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = ps_select_all.executeQuery();

            while(resultSet.next()) {
                listarPacientes.add(mapearPaciente(resultSet));
            }
        } catch (Exception e) {
            System.out.println("ERROR AL BUSCAR PACIENTES: "+e.getMessage());
        }

        return listarPacientes;
    }

    @Override
    public Paciente buscarPorString(String nombre) {
        Connection connection = null;
        Paciente paciente = null;
        Domicilio domicilio = null;

        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement ps_select_nombre = connection.prepareStatement(SQL_SELECT_NOMBRE);
            ps_select_nombre.setString(1, nombre);
            ResultSet resultSet = ps_select_nombre.executeQuery();
            DomicilioDAOH2 domicilioDAOH2 = new DomicilioDAOH2();

            while (resultSet.next()) {
                domicilio = domicilioDAOH2.buscar(resultSet.getInt(6));
                paciente = new Paciente(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getDate(5).toLocalDate(), resultSet.getString(domicilio.getId()),
                        resultSet.getString(7));
            }
        } catch (Exception e) {
            System.out.println("ERROR AL BUSCAR PACIENTE POR NOMBRE: " + e.getMessage());
        }

        return paciente;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Paciente eliminado: " + id);
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR PACIENTES: "+e.getMessage());
        }
    }

    @Override
    public Paciente modificar(Paciente paciente) {
        Connection connection = null;

        try {
            connection = BD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setInt(3, paciente.getNumeroContacto());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setString(5, paciente.getDomicilio());
            preparedStatement.setString(6, paciente.getEmail());
            preparedStatement.setInt(7, paciente.getId());

            preparedStatement.executeUpdate();

            System.out.println("Paciente modificado: " + paciente.getId());

            return paciente;
        } catch (Exception e) {
            System.out.println("ERROR AL MODIFICAR PACIENTE: "+e.getMessage());
        }

        return null;
    }

	private Paciente mapearPaciente(ResultSet rs) throws SQLException {
		return new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5).toLocalDate(),
				rs.getString(6), rs.getString(7));
	}
}
