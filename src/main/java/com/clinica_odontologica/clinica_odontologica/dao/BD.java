package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BD {

	private static final String SQL_DROP_CREATE_DOMICILIOS = "DROP TABLE IF EXISTS DOMICILIOS; CREATE TABLE DOMICILIOS(ID INT AUTO_INCREMENT PRIMARY KEY, CALLE VARCHAR(100) NOT NULL, NUMERO INT NOT NULL,LOCALIDAD VARCHAR(100) NOT NULL, PROVINCIA VARCHAR(100) NOT NULL)";
	private static final String SQL_DROP_CREATE_PACIENTES = "DROP TABLE IF EXISTS PACIENTES; CREATE TABLE PACIENTES(ID INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL, NUMEROCONTACTO INT NOT NULL, FECHAINGRESO DATE NOT NULL, DOMICILIO_ID INT NOT NULL, EMAIL VARCHAR(100) NOT NULL)";
	private static final String SQL_DROP_CREATE_ODONTOLOGOS = "DROP TABLE IF EXISTS ODONTOLOGOS; CREATE TABLE ODONTOLOGOS(ID INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL, MATRICULA INT NOT NULL)";
	private static final String prueba = "INSERT INTO DOMICILIOS(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES ('siempre viva', '723', 'Springfield', 'USA'), ('calle falsa', '123', 'Springfield', 'USA');"
			+ "INSERT INTO PACIENTES (NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES ('Homero', 'Simpson', '11223344', '2025-10-09', '1', 'homero@disney.com'), ('Marge', 'Simpson', '99887766', '2025-08-09', '2', 'marge@disney.com');"
			+ "INSERT INTO ODONTOLOGOS (NOMBRE, APELLIDO,MATRICULA) VALUES ('Luciano', 'Sicolo', '11223344'), ('Juan', 'Perez', '322333123')";

	public static void crearTablas() {
		// Primero llamo a la conexion
		Connection connection = null;

		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			statement.execute(SQL_DROP_CREATE_DOMICILIOS);
			statement.execute(SQL_DROP_CREATE_PACIENTES);
			statement.execute(SQL_DROP_CREATE_ODONTOLOGOS);
			statement.execute(prueba);
			System.out.println("DATOS CREADOS");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	public static Connection getConnection() throws Exception {
		Class.forName("org.h2.Driver");
		return DriverManager.getConnection("jdbc:h2:mem:~/clinicaFeliz", "sa", "sa");
	}
}
