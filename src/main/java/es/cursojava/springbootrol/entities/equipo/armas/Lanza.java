package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LANZA")
public class Lanza extends Armas {

    public Lanza() {
        super(
            "Lanza",    // nombre
            2,          // nivelRequerido
            4,          // peso
            70,         // durabilidad
            "PUNZANTE", // tipoDaño
            2,          // alcance
            65,         // precision
            7,          // puntosDaño
            15          // probCritico
        );
    }
}
