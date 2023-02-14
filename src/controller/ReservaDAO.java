package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;

import model.Reserva;

public class ReservaDAO {
	final public Connection con;
	private ArrayList<Reserva> listaReservas = new ArrayList<>();
	
	public ReservaDAO(Connection con) {
		this.con = con;
	}
	public void guardar(String nroReserva, String fechaCheckInString, String fechaCheckOutString, String txtPrecio, String metodoDePagoValor) {
		try {
			try(con){
				PreparedStatement statement = con.prepareStatement("INSERT INTO reservas"
						+ " (ID, FechaEntrada, FechaSalida, Valor, MetodoDePago)"
						+ " VALUES (?,?,?,?,?);");
				statement.setString(1, nroReserva);
				statement.setString(2, fechaCheckInString);
				statement.setString(3, fechaCheckOutString);
				statement.setString(4, txtPrecio);
				statement.setString(5, metodoDePagoValor);
				
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Reserva> listar() {
		try {
			try(con){
				final PreparedStatement statement = con.prepareStatement("SELECT ID, FechaEntrada,"
						+ " FechaSalida, Valor, MetodoDePago FROM reservas");
				try(statement){
					statement.execute();
					
					final ResultSet result = statement.getResultSet();;
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
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void eliminar(int nroReservaSeleccionado) {
		try {
			try(con){
				final PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE ID = ?");
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
		int idReserva = (int) tablaActiva.getValueAt(filaSeleccionada, 0);
		String fechaEntrada = (String) tablaActiva.getValueAt(filaSeleccionada, 1);
		String fechaSalida = (String) tablaActiva.getValueAt(filaSeleccionada, 2);
		String costo = String.valueOf(tablaActiva.getValueAt(filaSeleccionada, 3));
		String metodoDePago =(String) tablaActiva.getValueAt(filaSeleccionada, 4);
		
		try {
			try(con){
				final PreparedStatement statement = con.prepareStatement("UPDATE reservas SET FechaEntrada = ?, FechaSalida = ?, Valor = ?, MetodoDePago = ? WHERE ID = ?");
				
				try(statement){
					statement.setString(1, fechaEntrada);
					statement.setString(2, fechaSalida);
					statement.setString(3, costo);
					statement.setString(4, metodoDePago);
					statement.setInt(5, idReserva);
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
