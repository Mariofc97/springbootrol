package model;

public class EquipamientoDto {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getDurabilidad() {
		return durabilidad;
	}

	public void setDurabilidad(int durabilidad) {
		this.durabilidad = durabilidad;
	}

	public int getNivelRequerido() {
		return nivelRequerido;
	}

	public void setNivelRequerido(int nivelRequerido) {
		this.nivelRequerido = nivelRequerido;
	}

	public Long getPersonajeId() {
		return personajeId;
	}

	public void setPersonajeId(Long personajeId) {
		this.personajeId = personajeId;
	}

	private Long id;
	private String tipo;
	private String nombre;
	private int peso;
	private int durabilidad;
	private int nivelRequerido;
	private Long personajeId;
	
	public EquipamientoDto(Long id, String tipo, String nombre, int peso, int durabilidad, int nivelRequerido,
			Long personajeId) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre = nombre;
		this.peso = peso;
		this.durabilidad = durabilidad;
		this.nivelRequerido = nivelRequerido;
		this.personajeId = personajeId;
	}
	
	@Override
	public String toString() {
		return "EquipamientoDto [id=" + id + ", tipo=" + tipo + ", nombre=" + nombre + ", peso=" + peso
				+ ", durabilidad=" + durabilidad + ", nivelRequerido=" + nivelRequerido + ", personajeId=" + personajeId
				+ "]";
	}
	
	
	
	
	
	
}
