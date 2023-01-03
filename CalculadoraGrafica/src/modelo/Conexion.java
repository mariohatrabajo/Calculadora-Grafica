package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	public Connection connection;
	
	public Conexion() {
		// Cargar el driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Driver cargado");

		// Establecer la conexion con la BD
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/visualizadorfunciones?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"marisma", "marisma");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Conexión establecida");
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
