package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Nacionalidades {
	private ArrayList<Nacionalidad> listado = new ArrayList<>();
	
	public Nacionalidades() { 
		List<String> nacionalidades = Arrays.asList("Afgana","Alemana","Arabe","Argentina",
				"Australiana","Belga","Boliviana","Brasileña","Camboyana","Canadiense",
				"Chilena","China","Colombiana","Coreana","Costarriquense","Cubana","Danesa",
				"Ecuatoriana","Egipcia","Salvadoreña","Escocesa","Española","Estadounidense",
				"Estonia","Etiope","Filipina","Finlandesa","Francesa","Galesa","Griega",
				"Guatemalteca","Haitiana","Holandesa","Hondureña","Indonesa","Inglesa",
				"Iraquí","Iraní","Irlandesa","Israelí","Italiana","Japonesa","Jordana",
				"Laosiana","Letona","Malaya","Marroquí","Mexicana","Nicaragüense","Noruega",
				"Neozelandesa","Panameña","Paraguaya","Peruana","Polaca","Portuguesa",
				"Puertorriqueña","Dominicana","Rumana","Rusa","Sueca","Suiza","Tailandesa",
				"Taiwanesa","Turca","Ucraniana","Uruguaya","Venezolana","Vietnamita");
		// Ordena las nacionalidades por si no lo estan.
		Collections.sort(nacionalidades);
		
		for(String nacionalidad : nacionalidades) {
			listado.add(new Nacionalidad(nacionalidad));
		}
	}
	
	public ArrayList<Nacionalidad> getListado() {
		return listado;
	}
}
