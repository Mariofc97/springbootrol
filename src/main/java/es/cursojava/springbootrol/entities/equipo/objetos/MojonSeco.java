package es.cursojava.springbootrol.entities.equipo.objetos;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity	
@DiscriminatorValue("MOJON_SECO")

public class MojonSeco extends Equipamiento {
public MojonSeco() {
		super("Mojón Seco", 1, 1, 5); // ajusta valores
	}
}
