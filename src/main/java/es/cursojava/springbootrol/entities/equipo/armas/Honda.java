package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("HONDA")
public class Honda extends Armas {

    public Honda() {
        super(
            "Honda",   // nombre
            1,         // nivelRequerido
            2,         // peso
            50,        // durabilidad
            "LATIGAZO",  // tipoDaño
            3,         // alcance
            60,        // precision
            5,         // puntosDaño
            10         // probCritico
        );
    }
}
