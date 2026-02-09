package es.cursojava.springbootrol.entities.equipo.objetos;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMIDA")
public class Comida extends Equipamiento {

	public Comida() {
		super("Comida", 1, 1, 2); // ajusta valores
	}

}
