package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Huesped;
import model.Reserva;

public class BusquedaAlgoritmo {
	final public Connection con;
	private ArrayList<Huesped> listaHuespedes;
	private ArrayList<Reserva> listaReservas;
	
	public BusquedaAlgoritmo(Connection con) {
		this.con = con;
	}
	
	public ArrayList<Reserva> buscarEnReservas(String valorABuscar) {
		try {
			try(con){
				PreparedStatement statement = con.prepareStatement("SELECT * FROM reservas"
						+ " WHERE CONCAT(ID,FechaEntrada,FechaSalida,Valor,MetodoDePago) LIKE ?;");
				try(statement){
					statement.setString(1, "%" + valorABuscar + "%");
					statement.execute();
					
					final ResultSet result = statement.getResultSet();
					
					try(result){
						ArrayList<Reserva> listaReservas = new ArrayList<>();
						
						while(result.next()) {
							Reserva reserva = new Reserva(result.getInt("ID"), result.getString("FechaEntrada"),
									result.getString("FechaSalida"), result.getDouble("Valor"), result.getString("MetodoDePago"));
							
							listaReservas.add(reserva);
						}
						this.listaReservas = listaReservas;
						
						return listaReservas;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Huesped> buscarEnHuespedes(String valorABuscar) {
		try {
			try(con){
				PreparedStatement statement = con.prepareStatement("SELECT * FROM huespedes"
						+ " WHERE CONCAT(ID,Nombre,Apellido,FechaNacimiento,Nacionalidad,Telefono,IdReserva) LIKE ?;");
				try(statement){
					statement.setString(1, "%" + valorABuscar + "%");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
