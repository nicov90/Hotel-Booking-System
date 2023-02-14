package model;

public class Reserva {
	private int id;
	private String fechaEntrada;
	private String fechaSalida;
	private Double valor;
	private String metodoDePago;
	
	public Reserva(int id, String fechaEntrada, String fechaSalida,
			Double valor, String metodoDePago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.metodoDePago = metodoDePago;
	}

	public int getId() {
		return id;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public Double getValor() {
		return valor;
	}

	public String getMetodoDePago() {
		return metodoDePago;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public void setMetodoDePago(String metodoDePago) {
		this.metodoDePago = metodoDePago;
	}
}
