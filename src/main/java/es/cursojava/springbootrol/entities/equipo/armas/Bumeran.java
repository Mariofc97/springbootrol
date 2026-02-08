package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BUMERAN")
public class Bumeran extends Armas {

    public Bumeran() {
        super(
            "Bumerán", // nombre
            1,         // nivelRequerido
            2,         // peso
            55,        // durabilidad
            "GOLPE",   // tipoDaño
            3,         // alcance
            75,        // precision
            5,         // puntosDaño
            12         // probCritico
        );
    }
}
