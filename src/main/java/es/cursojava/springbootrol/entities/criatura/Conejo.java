package es.cursojava.springbootrol.entities.criatura;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONEJO")
public class Conejo extends Criatura{

	

//	public Conejo(String nombre, String alias, int nivel, int experiencia, int puntosVida, int puntosAtaque, String tipoAtaque) {
//		super("Conejo", alias, 1, 0, 25, 2, "Patada salto");
//	}

	public Conejo() {
		super("Conejo", null, 1, 3, 50, 2, "Patada salto");
		// TODO Auto-generated constructor stub
	}
	




}
