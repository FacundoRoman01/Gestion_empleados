package dao;

import excepciones.DaoExeception; // Importamos tu excepción personalizada
import java.io.IOException; // Para manejar errores de I/O (lectura de archivos)
import java.io.InputStream; // Para leer el archivo de propiedades
import java.sql.Connection; // La interfaz de JDBC para la conexión
import java.sql.DriverManager; // La clase de JDBC para obtener conexiones
import java.sql.SQLException; // La excepción de JDBC para errores de SQL
import java.util.Properties; // Para leer el archivo .properties

public class ConexionDB {
	// 1. Atributos estáticos para almacenar la configuración de la BD
	private static String DB_URL;
	private static String DB_USERNAME;
	private static String DB_PASSWORD;
	
	

	// 2. Bloque estático: Se ejecuta una única vez cuando la clase se carga en
	// memoria
	static {
		Properties props = new Properties(); // Objeto para cargar propiedades de un archivo
		// Intentamos cargar el archivo application.properties
		try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("application.properties")) {

			if (input == null) {
				// Si el archivo no se encuentra, lanzamos nuestra EmpleadoException
				throw new DaoExeception(
						"No se encontró el archivo application.properties. Asegúrate de que esté en src/main/resources/.");
			}
			
			props.load(input); // Cargar las propiedades desde el InputStream

			// Asignamos los valores leídos a nuestros atributos estáticos
			DB_URL = props.getProperty("db.url");
			DB_USERNAME = props.getProperty("db.username");
			DB_PASSWORD = props.getProperty("db.password");

			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (IOException e) {
			throw new DaoExeception("Error al cargar las propiedades de la base de datos: " + e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			// Si la clase del driver no se encuentra (ej. el JAR no está en el classpath)
			throw new DaoExeception(
					"No se encontró el driver JDBC de MySQL. Asegúrate de que mysql-connector-java.jar esté en el classpath.",
					e);
		}

	}
	
	
	

	// 3. Método para obtener una conexión a la base de datos
	public static Connection getConnection() throws SQLException {
		// DriverManager intenta establecer una conexión utilizando la URL, usuario y
		// contraseña.
		// Si la conexión falla (ej. credenciales incorrectas, BD no disponible), lanza
		// una SQLException.
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	// 4. Método para cerrar una conexión de forma segura
	public static void closeConnection(Connection conn) {
		if (conn != null) { // Verificamos que la conexión no sea nula antes de intentar cerrarla
			try {
				conn.close(); // Intentamos cerrar la conexión
			} catch (SQLException e) {
				// Si hay un error al cerrar la conexión, lo imprimimos pero no lanzamos
				// una EmpleadoException fatal porque la conexión ya está cerrada o en proceso.
				System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
			}
		}
	}

}
