package entities.equipo.objetos;

import entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("PIEDRA")

public class Piedra extends Equipamiento{

public Piedra() {
		super("Piedra", 8, 1, 100); // ajusta valores
	
	
	
}
	
	}
	
	
	
	
	
	


