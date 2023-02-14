package model;

import java.util.ArrayList;

public class MetodosDePago {
	private ArrayList<MetodoDePago> listado = new ArrayList<>();
	private MetodoDePago tarjetaDeCredito = new MetodoDePago("Tarjeta de Credito");
	private MetodoDePago tarjetaDeDebito = new MetodoDePago("Tarjeta de Debito");
	private MetodoDePago dineroEnEfectivo = new MetodoDePago("Dinero en Efectivo");
	
	public MetodosDePago() {
		listado.add(tarjetaDeCredito);
		listado.add(tarjetaDeDebito);
		listado.add(dineroEnEfectivo);
	}
	public ArrayList<MetodoDePago> getListado(){
		return listado;
	}
}
