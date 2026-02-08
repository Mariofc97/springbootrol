package es.cursojava.springbootrol.entities.criatura;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;



@Entity 
@DiscriminatorValue("LOBO")

public class Lobo extends Criatura {
	
	
	
	public Lobo() {
		super("Lobo", null, 1, 8, 30, 35, "Mordisco feroz");
		// TODO Auto-generated constructor stub
	}

}
