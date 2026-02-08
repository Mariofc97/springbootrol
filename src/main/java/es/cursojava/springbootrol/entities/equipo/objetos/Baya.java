package es.cursojava.springbootrol.entities.equipo.objetos;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity	
@DiscriminatorValue("BAYA")
public class Baya extends Equipamiento {

	public Baya() {
		super("Baya", 1, 1, 2); // ajusta valores
	}
	

}
