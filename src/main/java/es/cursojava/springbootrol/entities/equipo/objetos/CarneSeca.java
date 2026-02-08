package es.cursojava.springbootrol.entities.equipo.objetos;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity	
@DiscriminatorValue("CARNE_SECA")
public class CarneSeca extends Equipamiento  {
	
		public CarneSeca() {
		super("Carne Seca", 1, 1, 3); // ajusta valores
	}

}
