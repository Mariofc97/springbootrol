package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAZAMARIPOSAS")
public class Cazamariposas extends Armas {

    public Cazamariposas() {
        super(
            "Cazamariposas", // nombre
            1,               // nivelRequerido
            2,               // peso
            40,              // durabilidad
            "GOLPE",         // tipoDaño
            2,               // alcance
            80,              // precision
            4,               // puntosDaño
            8                // probCritico
        );
    }
}
