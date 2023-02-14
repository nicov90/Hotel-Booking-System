package model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class CalculosReserva {
	private static String fechaCheckInString;
	private static String fechaCheckOutString;
	private int precioPorDia = 20;
	
	public void setFechas(JDateChooser txtFechaE, JDateChooser txtFechaS, JTextField txtValor) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			// Para validar las fechas según la fecha actual.
			LocalDate currentDate =  Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaCheckIn = txtFechaE.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaCheckOut = txtFechaS.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			fechaCheckInString = formatter.format(fechaCheckIn);
			fechaCheckOutString = formatter.format(fechaCheckOut);
			
			long valorReserva = (fechaCheckOut.toEpochDay() - fechaCheckIn.toEpochDay()) * precioPorDia;
			
			if(fechaCheckIn.isAfter(currentDate)) {
				if(txtFechaS.getDate().after(txtFechaE.getDate()) &&
						valorReserva > 0) {
					txtValor.setText(String.valueOf(valorReserva));
				}else {
					JLabel errorFecha = new JLabel("La fecha de Check-out es incorrecta");
					JOptionPane.showMessageDialog(null, errorFecha, "Fecha incorrecta", JOptionPane.ERROR_MESSAGE);
					valorReserva = 0;
					txtFechaS.setDate(null);
					txtValor.setText("");
					throw new RuntimeException("La fecha de Check-out es incorrecta.");
				}
			}else {
				JLabel errorFecha = new JLabel("<html>La fecha de Check-in es incorrecta. <br> La fecha debe ser como mínimo el dia siguiente de hoy.</html>");
				JOptionPane.showMessageDialog(null, errorFecha, "Fecha incorrecta", JOptionPane.ERROR_MESSAGE);
				valorReserva = 0;
				txtFechaE.setDate(null);
				txtFechaS.setDate(null);
				txtValor.setText("");
				throw new RuntimeException("La fecha de Check-in es incorrecta.");
			}
		}catch(NullPointerException e) {
			throw new RuntimeException(e);
		}
	}
	public String getFechaCheckInString() {
		return CalculosReserva.fechaCheckInString;
	}
	public String getFechaCheckOutString() {
		return CalculosReserva.fechaCheckOutString;
	}
}
