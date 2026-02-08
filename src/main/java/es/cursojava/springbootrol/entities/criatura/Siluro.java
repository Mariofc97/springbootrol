package es.cursojava.springbootrol.entities.criatura;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SILURO")


public class Siluro extends Criatura {
	
	
	
	public Siluro() {
		super("Siluro", null, 10, 0, 30, 30, "Mordisco");
	}

}
