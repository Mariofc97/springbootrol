package es.cursojava.springbootrol.entities.equipo.objetos;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;




@Entity
@DiscriminatorValue("POCION")

public class Pocion extends Equipamiento {
	public int getPuntosDeVida() {
		return puntosDeVida;
	}


	public void setPuntosDeVida(int puntosDeVida) {
		this.puntosDeVida = puntosDeVida;
	}


	private int puntosDeVida;
	
	
	public Pocion(String nombre, int nivelRequerido, int peso, int durabilidad, int puntosDeVida) {
		super(nombre, nivelRequerido, peso, durabilidad);
		this.puntosDeVida = puntosDeVida;
	}


	public Pocion() {
		super("Pocion", 1, 1, 1); // ajusta valores
	}
	
	

}
