package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRAMPA")
public class Trampa extends Armas {

    public Trampa() {
        super(
                "Trampa", // nombre
                3,         // nivelRequerido
                5,         // peso
                45,        // durabilidad
                "Atrapamiento",   // tipoDaño
                3,         // alcance
                75,        // precision
                3,         // puntosDaño
                10         // probCritico
            );
	}
}
