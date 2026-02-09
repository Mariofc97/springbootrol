package es.cursojava.springbootrol.entities.equipo.escudos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESCUDO_MADERA")
public class EscudoMadera extends Escudos {

    public EscudoMadera() {
        super(
            "Escudo de Madera", // nombre
            2,                  // nivelRequerido
            5,                  // peso
            40,                 // durabilidad
            3                   // puntosResistencia
        );
    }
}
