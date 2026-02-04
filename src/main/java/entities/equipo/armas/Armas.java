package entities.equipo.armas;



import entities.equipo.Equipamiento;
import jakarta.persistence.Column;



public abstract class Armas extends Equipamiento {

	

	public boolean isEquipada() {
		return equipada;
	}



	public void setEquipada(boolean equipada) {
		this.equipada = equipada;
	}



	@Column(name="tipo_danio")
	private String tipoDaño;

	@Column(name="alcance")
	private int alcance;

	@Column(name="precision")
	private int precision;

	@Column(name="puntos_danio")
	private int puntosDaño;

	@Column(name="prob_critico")
	private int probCritico;
	
	@Column(name="equipada")
	private boolean equipada;



	public Armas(String nombre, int nivelRequerido, int peso, int durabilidad) {

		super(nombre, nivelRequerido, peso, durabilidad);

		// TODO Auto-generated constructor stub

	}



	public Armas(String nombre, int nivelRequerido, int peso, int durabilidad, String tipoDaño, int alcance,

			int precision, int puntosDaño, int probCritico) {

		super(nombre, nivelRequerido, peso, durabilidad);

		this.tipoDaño = tipoDaño;

		this.alcance = alcance;

		this.precision = precision;

		this.puntosDaño = puntosDaño;

		this.probCritico = probCritico;

	}

	



	public Armas(String tipoDaño, int alcance, int precision, int puntosDaño, int probCritico) {

		super();

		this.tipoDaño = tipoDaño;

		this.alcance = alcance;

		this.precision = precision;

		this.puntosDaño = puntosDaño;

		this.probCritico = probCritico;

	}
	
	



	public String getTipoDaño() {

		return tipoDaño;

	}



	public void setTipoDaño(String tipoDaño) {

		this.tipoDaño = tipoDaño;

	}



	public int getAlcance() {

		return alcance;

	}



	public void setAlcance(int alcance) {

		this.alcance = alcance;

	}



	public int getPrecision() {

		return precision;

	}



	public void setPrecision(int precision) {

		this.precision = precision;

	}



	public int getPuntosDaño() {

		return puntosDaño;

	}



	public void setPuntosDaño(int puntosDaño) {

		this.puntosDaño = puntosDaño;

	}



	public int getProbCritico() {

		return probCritico;

	}



	public void setProbCritico(int probCritico) {

		this.probCritico = probCritico;

	}



	@Override

	public String toString() {

		return "Armas [tipoDaño=" + tipoDaño + ", alcance=" + alcance + ", precision=" + precision + ", puntosDaño="

				+ puntosDaño + ", probCritico=" + probCritico + "]";

	}

	

	

	

	//TODO

	//IMPLEMENTA INTERFAZ EQUIPABLE



}


