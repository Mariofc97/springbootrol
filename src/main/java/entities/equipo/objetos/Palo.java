
package entities.equipo.objetos;

import entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("PALO")
	

public class Palo extends Equipamiento {

	

public Palo() {
		super("Palo", 2, 1, 100); // ajusta valores
	}
}
