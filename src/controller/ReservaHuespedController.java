package controller;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import factory.ConnectionFactory;
import model.CalculosReserva;
import model.Huesped;
import model.MetodoDePago;
import model.MetodosDePago;
import model.Nacionalidad;
import model.Nacionalidades;
import model.Reserva;
import views.Busqueda;
import views.CustomTableModel;

public class ReservaHuespedController {
	String nombreValor = null;
	String apellidoValor = null;
	String telefonoValor = null;
	String fechaNacimientoFormateado;
	Nacionalidad nacionalidadValor = null;
	private static String fechaCheckInString;
	private static String fechaCheckOutString;
	private static String txtPrecio;
	private static String metodoDePagoValor;
	
	public void calcularReserva(JDateChooser txtFechaE, JDateChooser txtFechaS, JTextField txtValor) {
		CalculosReserva calculosReserva = new CalculosReserva();
		calculosReserva.setFechas(txtFechaE, txtFechaS, txtValor);
	}
	public DefaultComboBoxModel<MetodoDePago> getPaymentsModel() {
		ArrayList<MetodoDePago> listado = new MetodosDePago().getListado();
		DefaultComboBoxModel<MetodoDePago> model = new DefaultComboBoxModel<MetodoDePago>();
		for(MetodoDePago item : listado) {
			model.addElement(item);
		}
		return model;
	}
	public DefaultComboBoxModel<Nacionalidad> getNationalityModel(){
		ArrayList<Nacionalidad> listado = new Nacionalidades().getListado();
		DefaultComboBoxModel<Nacionalidad> model = new DefaultComboBoxModel<Nacionalidad>();
		for(Nacionalidad item : listado) {
			model.addElement(item);
		}
		return model;
	}
	public String setNroReserva() {
		String generatedNumber = null;
		do {
			generatedNumber = String.valueOf((int) (Math.random()*999999999));
		}while(generatedNumber.length() != 9);
		
		return generatedNumber;
	}
	
	public void validarCamposHuesped(JTextField txtNombre, JTextField txtApellido, JDateChooser txtFechaN,
			JComboBox<Nacionalidad> txtNacionalidad, JTextField txtTelefono) {
		String regexNombre = "^[a-zA-ZñÑ\\s]*$";
		String regexApellido = "^[a-zA-ZñÑ]*$";
		String regexNumeros = "^[0-9]+$";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		LocalDate currentDate =  Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaNacimiento;
		Period fechaDiferencia;
		
		nombreValor = txtNombre.getText();
		apellidoValor = txtApellido.getText();
		
		nacionalidadValor = (Nacionalidad) txtNacionalidad.getSelectedItem();
		telefonoValor = txtTelefono.getText();
		
		// Nombre
		if(nombreValor.length() >= 3) {
			if(!nombreValor.matches(regexNombre)) {
				JOptionPane.showMessageDialog(null, "El campo 'Nombre' debe tener solo caracteres alfabéticos.");
				throw new RuntimeException("Campo no cumple los requisitos.");
			}
		}else {
			JOptionPane.showMessageDialog(null, "El campo 'Nombre' debe tener 3 o más caracteres.");
			throw new RuntimeException("Campo no cumple los requisitos.");
		}
		
		// Apellido
		if(apellidoValor.length() >= 3) {
			if(!apellidoValor.matches(regexApellido)) {
				JOptionPane.showMessageDialog(null, "El campo 'Apellido' debe tener solo caracteres alfabéticos y sin espacios.");
				throw new RuntimeException("Campo no cumple los requisitos.");
			}
		}else {
			JOptionPane.showMessageDialog(null, "El campo 'Apellido' debe tener más de 3 caracteres.");
			throw new RuntimeException("Campo no cumple los requisitos.");
		}
		// Fecha de Nacimiento
		try {
			fechaNacimiento = txtFechaN.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			fechaDiferencia = Period.between(fechaNacimiento, currentDate);
			fechaNacimientoFormateado = formatter.format(txtFechaN.getDate());
		}catch(NullPointerException npe) {
			JOptionPane.showMessageDialog(null, "El campo de 'Nacimiento' no puede estar vacio.");
			throw new RuntimeException("Campo no cumple los requisitos.");
		}
		if(fechaDiferencia.getYears() < 18) {
			JOptionPane.showMessageDialog(null, "Debe tener al menos 18 años para poder registrarse.");
			throw new RuntimeException("Campo no cumple los requisitos.");
		}
		// Nacionalidad
		if(nacionalidadValor == null) {
			JOptionPane.showMessageDialog(null, "Verifique el campo de 'Nacionalidad'.");
			throw new RuntimeException("Campo no cumple los requisitos.");
		}
		// Teléfono
		if(telefonoValor.length() < 6 || !telefonoValor.matches(regexNumeros)) {
			JOptionPane.showMessageDialog(null, "El campo de 'Teléfono' no es válido.");
			throw new RuntimeException("Campo no cumple los requisitos.");
		}
	}
	// Guarda los valores en reservaController para utilizarlos luego.
	public void datosReservaHolder(String fechaCheckInString, String fechaCheckOutString, String txtPrecio, String metodoDePagoValor) {
		ReservaHuespedController.fechaCheckInString = fechaCheckInString;
		ReservaHuespedController.fechaCheckOutString = fechaCheckOutString;
		ReservaHuespedController.txtPrecio = txtPrecio;
		ReservaHuespedController.metodoDePagoValor = metodoDePagoValor;
	}
	public void guardarDatosReserva(String nroReserva) {
		Connection con = new ConnectionFactory().crearConexion();
		ReservaDAO reservaDAO = new ReservaDAO(con);
		reservaDAO.guardar(nroReserva, ReservaHuespedController.fechaCheckInString,ReservaHuespedController.fechaCheckOutString,
				ReservaHuespedController.txtPrecio,ReservaHuespedController.metodoDePagoValor);
	}
	public void guardarRegistroHuesped(String nroReserva) {
		String nacionalidadString = nacionalidadValor.toString();

		Connection con = new ConnectionFactory().crearConexion();
		HuespedDAO huespedDAO = new HuespedDAO(con);
		huespedDAO.guardar(nombreValor,apellidoValor,fechaNacimientoFormateado,
				nacionalidadString,telefonoValor,nroReserva);
	}
	public void listarReservas(CustomTableModel modelo) {
		ArrayList<Reserva> listaDeReservas = new ReservaDAO
				(new ConnectionFactory().crearConexion()).listar();
		for(Reserva reserva : listaDeReservas) {
			modelo.addRow(new Object[] {
					reserva.getId(),reserva.getFechaEntrada(),reserva.getFechaSalida(),
					reserva.getValor(),reserva.getMetodoDePago()
			});
		}
	}
	public void listarHuespedes(CustomTableModel modeloH) {
		ArrayList<Huesped> listaDeHuespedes = new HuespedDAO
				(new ConnectionFactory().crearConexion()).listar();
		for(Huesped huesped : listaDeHuespedes) {
			modeloH.addRow(new Object[] {
					huesped.getId(),huesped.getNombre(),huesped.getApellido(),
					huesped.getFechaDeNacimiento(),huesped.getNacionalidad(),
					huesped.getTelefono(),huesped.getIdReserva()
			});
		}
	}
	public void eliminarSeleccionado(String nombrePanelActivo, JTable tablaActiva, int filaSeleccionada) {
		int nroReservaSeleccionado = 0;

		// Se halla el valor de nroDeReserva según la tabla seleccionada
		if(nombrePanelActivo == "Huéspedes" || nombrePanelActivo == "Huespedes") {
			nroReservaSeleccionado = (int) tablaActiva.getValueAt(filaSeleccionada, 6);
		}else if(nombrePanelActivo == "Reservas") {
			nroReservaSeleccionado = (int) tablaActiva.getValueAt(filaSeleccionada, 0);
		}
		new HuespedDAO( new ConnectionFactory().crearConexion() ).eliminar(nroReservaSeleccionado);
		new ReservaDAO( new ConnectionFactory().crearConexion() ).eliminar(nroReservaSeleccionado);
		JOptionPane.showMessageDialog(null, "Los registros del huésped y su respectiva reserva han sido eliminados.");
	}
	public void limpiarListaModelo(CustomTableModel modelo) {
		while(modelo.getRowCount() > 1) {
		    modelo.removeRow(1);
		}
	}
	public void limpiarListaModeloH(CustomTableModel modeloH) {
		while(modeloH.getRowCount() > 1) {
		    modeloH.removeRow(1);
		}
	}
	public void limpiarLista(CustomTableModel modelo, CustomTableModel modeloH) {
		while(modelo.getRowCount() > 1) { // Me permite conservar la primera fila con los titulos
		    modelo.removeRow(1);
		}
		while(modeloH.getRowCount() > 1) { // Me permite conservar la primera fila con los titulos
		    modeloH.removeRow(1);
		}
	}
	public void editarRegistros(String nombrePanelActivo, JTable tablaActiva, int filaSeleccionada) {
		// Se halla el valor de nroDeReserva según la tabla seleccionada
		if(nombrePanelActivo == "Huéspedes" || nombrePanelActivo == "Huespedes") {
			new HuespedDAO( new ConnectionFactory().crearConexion() ).editar(tablaActiva, filaSeleccionada);
			
		}else if(nombrePanelActivo == "Reservas") {
			new ReservaDAO( new ConnectionFactory().crearConexion() ).editar(tablaActiva, filaSeleccionada);
		}
		JOptionPane.showMessageDialog(null, "Cambios guardados con éxito!", "Edición exitosa", 1);
	}
	public void buscar(CustomTableModel modelo, CustomTableModel modeloH, String nombrePanelActivo, JTable tablaActiva, String valorABuscar) {
		
		if(nombrePanelActivo == "Huéspedes" || nombrePanelActivo == "Huespedes") {
			limpiarListaModeloH(modeloH);
			
			ArrayList<Huesped> listaDeHuespedes = 
					new BusquedaAlgoritmo( new ConnectionFactory().crearConexion() ).buscarEnHuespedes(valorABuscar);
			
			for(Huesped huesped : listaDeHuespedes) {
				modeloH.addRow(new Object[] {
						huesped.getId(),huesped.getNombre(),huesped.getApellido(),
						huesped.getFechaDeNacimiento(),huesped.getNacionalidad(),
						huesped.getTelefono(),huesped.getIdReserva()
				});
			}
			
		}else if(nombrePanelActivo == "Reservas") {
			limpiarListaModelo(modelo);

			ArrayList<Reserva> listaDeReservas = 
					new BusquedaAlgoritmo( new ConnectionFactory().crearConexion() ).buscarEnReservas(valorABuscar);
			
			for(Reserva reserva : listaDeReservas) {
				modelo.addRow(new Object[] {
						reserva.getId(),reserva.getFechaEntrada(),reserva.getFechaSalida(),
						reserva.getValor(),reserva.getMetodoDePago()
				});
			}
		}
	}
}
