package com.clinica_odontologica.clinica_odontologica.dao;

import com.clinica_odontologica.clinica_odontologica.model.Domicilio;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;

import java.sql.*;
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

    @Override
    public Paciente guardar(Paciente paciente) {
        Paciente pacienteGuardado = null;
        Domicilio domicilio = null;
        Connection connection =  null;

        try {
            connection = BD.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getNumeroContacto());
            ps.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps.setString(5, paciente.getDomicilio());
            ps.setString(6, paciente.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                pacienteGuardado = new Paciente();
                pacienteGuardado.setId(rs.getInt(1));
                pacienteGuardado.setNombre(rs.getString(2));
                pacienteGuardado.setApellido(rs.getString(3));
                pacienteGuardado.setNumeroContacto(rs.getInt(4));
                pacienteGuardado.setFechaIngreso(rs.getDate(5).toLocalDate());
                pacienteGuardado.setDomicilio(rs.getString(6));
                pacienteGuardado.setEmail(rs.getString(7));
            }

            System.out.println("Paciente guardado: " + pacienteGuardado);

            return pacienteGuardado;
        } catch(Exception ex) {
            System.out.println("Error al intentar guardar Paciente: " + ex.getMessage());
        }


        return null;
    }

    @Override
    public Paciente bucar(Integer id) {
        Connection connection = null;
        Paciente paciente = null;
        Domicilio domicilio = null;

        try {
            connection = BD.getConnection();
            PreparedStatement ps_select_one = connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1, id);
            ResultSet resultSet = ps_select_one.executeQuery();
            DomicilioDAOH2 domicilioDAOH2 = new DomicilioDAOH2();

            while(resultSet.next()){
                domicilio = domicilioDAOH2.bucar(resultSet.getInt(6));
                paciente = new Paciente(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4), resultSet.getDate(5).toLocalDate(),
                        resultSet.getString(domicilio.getId()), resultSet.getString(7));
            }
        } catch (Exception e){
            System.out.println("ERROR AL BUSCAR PACIENTE: "+e.getMessage());
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
