package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;

import model.Huesped;

public class HuespedDAO {
	final public Connection con;
	private ArrayList<Huesped> listaHuespedes;
	
	public HuespedDAO(Connection con) {
		this.con = con;
	}
	public void guardar(String nombreValor, String apellidoValor, String fechaNacimientoFormateado,
			String nacionalidadString,String telefonoValor, String nroReserva) {
		try {
			try(con){
				// Setea el ID al máximo ID existente para que no hayan números espaciados al hacer auto increment.
				// Obtiene el valor máximo de ID
				PreparedStatement statement = con.prepareStatement("SELECT MAX(ID) FROM huespedes");
				ResultSet resultSet = statement.executeQuery();
				int maxId = 0;
				if (resultSet.next()) {
					maxId = resultSet.getInt(1);
				}
				
				// Asigna el valor máximo de ID + 1 a la columna de ID
				statement = con.prepareStatement("ALTER TABLE huespedes AUTO_INCREMENT = ?");
				statement.setInt(1, maxId + 1);
				statement.executeUpdate();
				
				statement = con.prepareStatement("INSERT INTO huespedes"
						+ " (Nombre, Apellido,FechaNacimiento, Nacionalidad, Telefono, IdReserva)"
						+ " VALUES (?,?,?,?,?,?);");
				statement.setString(1, nombreValor);
				statement.setString(2, apellidoValor);
				statement.setString(3, fechaNacimientoFormateado);
				statement.setString(4, nacionalidadString);
				statement.setString(5, telefonoValor);
				statement.setString(6, nroReserva);
				
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Huesped> listar() {
		try {
			try(con){
				final PreparedStatement statement = con.prepareStatement("SELECT ID, Nombre, Apellido, "
						+ "FechaNacimiento, Nacionalidad, Telefono, IdReserva FROM huespedes;");
				
				try(statement){
					statement.execute();
					
					final ResultSet result = statement.getResultSet();
					
					try(result){
						ArrayList<Huesped> listaHuespedes = new ArrayList<>();
						
						while(result.next()) {
							Huesped huesped = new Huesped(result.getInt("ID"),result.getString("Nombre"),
									result.getString("Apellido"),result.getString("FechaNacimiento"),
									result.getString("Nacionalidad"),result.getLong("Telefono"),result.getInt("IdReserva"));
							
							listaHuespedes.add(huesped);
						}
						this.listaHuespedes = listaHuespedes;
						
						return listaHuespedes;
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void eliminar(int nroReservaSeleccionado) {
		try {
			try(con){
				final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE IdReserva = ?");
				try(statement){
					statement.setInt(1, nroReservaSeleccionado);
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void editar(JTable tablaActiva, int filaSeleccionada) {
		String nombre = (String) tablaActiva.getValueAt(filaSeleccionada, 1);
		String apellido = (String) tablaActiva.getValueAt(filaSeleccionada, 2);
		String fechaNacimiento = (String) tablaActiva.getValueAt(filaSeleccionada, 3);
		String nacionalidad =(String) tablaActiva.getValueAt(filaSeleccionada, 4);
		String telefono = String.valueOf(tablaActiva.getValueAt(filaSeleccionada, 5));
		int idReserva = (int) tablaActiva.getValueAt(filaSeleccionada, 6);
		
		try {
			try(con){
				final PreparedStatement statement = con.prepareStatement("UPDATE huespedes SET Nombre = ?, "
						+ "Apellido = ?, Nacionalidad = ?, Telefono = ?, FechaNacimiento = ? WHERE IdReserva = ?;");
				try(statement){
					statement.setString(1, nombre);
					statement.setString(2, apellido);
					statement.setString(3, nacionalidad);
					statement.setString(4, telefono);
					statement.setString(5, fechaNacimiento);
					statement.setInt(6, idReserva);
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
