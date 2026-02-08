package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAÑA PESCAR")
public class CanaPescar extends Armas {

	
    public CanaPescar() {
        super(
                "Caña de Pescar", // nombre
                1,         // nivelRequerido
                2,         // peso
                55,        // durabilidad
                "LANZAMIENTO",   // tipoDaño
                3,         // alcance
                75,        // precision
                0,         // puntosDaño
                0         // probCritico
            );
	}
}
