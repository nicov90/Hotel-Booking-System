package model;

public class Huesped {
	private int id;
	private String nombre;
	private String apellido;
	private String fechaDeNacimiento;
	private String nacionalidad;
	private long telefono;
	private int idReserva;
	
	public Huesped(int id, String nombre, String apellido, 
			String fechaDeNacimiento, String nacionalidad, long telefono, int idReserva) {
		
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public long getTelefono() {
		return telefono;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaDeNacimiento(String fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	
}
