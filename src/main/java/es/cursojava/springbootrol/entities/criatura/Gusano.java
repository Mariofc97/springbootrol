package es.cursojava.springbootrol.entities.criatura;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GUSANO")
public class Gusano extends Criatura {


//	public Gusano(String nombre, String alias, int nivel, int experiencia, int puntosVida, int puntosAtaque, String tipoAtaque) {
//		super("Gusano", alias, 1, 0, 15, 2, "Disparo de seda");
//	}

	public Gusano() {
		super("Gusano", null, 1, 0, 70, 2, "Disparo de seda");
		// TODO Auto-generated constructor stub
	}



}
