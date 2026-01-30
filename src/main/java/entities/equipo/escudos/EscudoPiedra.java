package entities.equipo.escudos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESCUDO_PIEDRA")
public class EscudoPiedra extends Escudos {

    public EscudoPiedra() {
        super(
            "Escudo de Piedra", // nombre
            3,                  // nivelRequerido
            8,                  // peso
            60,                 // durabilidad
            5                   // puntosResistencia
        );
    }
}
