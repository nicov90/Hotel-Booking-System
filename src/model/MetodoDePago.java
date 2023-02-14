package model;

public class MetodoDePago {
	private String metodo;
	
	public MetodoDePago(String metodo) {
		this.metodo = metodo;
	}
	public String getMetodo() {
		return metodo;
	}
	@Override
	public String toString() {
		return this.metodo;
	}
}
