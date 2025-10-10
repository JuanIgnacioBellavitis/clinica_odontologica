package com.clinica_odontologica.clinica_odontologica.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BD {
	public static Connection getConnection() throws Exception {
		Class.forName("org.h2.Driver");
		return DriverManager.getConnection("jdbc:h2:mem:~/clinicaFeliz", "sa", "sa");
	}

	public static void crearTablas() {
		Connection connection = null;

		try {
			connection = getConnection();
			Statement statement = connection.createStatement();

			statement.execute("DROP TABLE IF EXISTS DOMICILIOS");
			statement.execute(
					"CREATE TABLE DOMICILIOS(ID INT AUTO_INCREMENT PRIMARY KEY, CALLE VARCHAR(100) NOT NULL, NUMERO INT NOT NULL, LOCALIDAD VARCHAR(100) NOT NULL, PROVINCIA VARCHAR(100) NOT NULL)");

			statement.execute("DROP TABLE IF EXISTS PACIENTES");
			statement.execute(
					"CREATE TABLE PACIENTES(ID INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL, NUMEROCONTACTO INT NOT NULL, FECHAINGRESO DATE NOT NULL, DOMICILIO_ID INT NOT NULL, EMAIL VARCHAR(100) NOT NULL)");

			statement.execute("DROP TABLE IF EXISTS ODONTOLOGOS");
			statement.execute(
					"CREATE TABLE ODONTOLOGOS(ID INT AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL, MATRICULA INT NOT NULL)");

			statement.execute(
					"INSERT INTO DOMICILIOS(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES ('siempre viva', '723', 'Springfield', 'USA')");
			statement.execute(
					"INSERT INTO DOMICILIOS(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES ('calle falsa', '123', 'Springfield', 'USA')");

			statement.execute(
					"INSERT INTO PACIENTES (NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES ('Homero', 'Simpson', '11223344', '2025-10-09', '1', 'homero@disney.com')");
			statement.execute(
					"INSERT INTO PACIENTES (NOMBRE, APELLIDO, NUMEROCONTACTO, FECHAINGRESO, DOMICILIO_ID, EMAIL) VALUES ('Marge', 'Simpson', '99887766', '2025-08-09', '2', 'marge@disney.com')");

			statement.execute(
					"INSERT INTO ODONTOLOGOS (NOMBRE, APELLIDO, MATRICULA) VALUES ('Luciano', 'Sicolo', '1231231')");
			statement.execute(
					"INSERT INTO ODONTOLOGOS (NOMBRE, APELLIDO, MATRICULA) VALUES ('Juan', 'Perez', '3213123')");

			System.out.println("Tablas creadas y datos cargados correctamente");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
