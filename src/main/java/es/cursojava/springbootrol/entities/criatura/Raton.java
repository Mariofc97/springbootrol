package es.cursojava.springbootrol.entities.criatura;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RATON")

public class Raton extends Criatura {

	public Raton(String nombre, String alias, int nivel, int experiencia, int puntosVida, int puntosAtaque,
			String tipoAtaque) {
		super("Raton", alias, 1, 0, 20, 1, "MordiscoInfeccioso");

	}

	public Raton() {
		super("Raton", null, 1, 0, 20, 1, "MordiscoInfeccioso");
	}

}
