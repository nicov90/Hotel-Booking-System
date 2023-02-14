package factory;

import java.awt.Cursor;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Clase de fábrica de conexiones. Contiene los métodos que permiten la manipulación
 * de la conexión con la base de datos.
 * 
 * @author Nicolas Valdez
 *
 */

public class ConnectionFactory {
	
	private DataSource dataSource;
	
	public ConnectionFactory(){
		
		// Para utilizar pool de conexiones
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("");
		
		// Setea máximo de conexiones disponibles simultaneamente.
		pooledDataSource.setMaxPoolSize(90);
		
		this.dataSource = pooledDataSource;
	}
	
	public Connection crearConexion(){
		/*return DriverManager.getConnection(
				"jdbc:mysql://localhost/hotel_alura", 
				"root", "");*/
		try {
			return this.dataSource.getConnection();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos.");
			throw new RuntimeException(e);
		}
		
	}
}
