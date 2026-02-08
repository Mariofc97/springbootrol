package es.cursojava.springbootrol.entities.equipo.objetos;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CUERDA")
public class Cuerda extends Equipamiento {

    public Cuerda() {
        super("Cuerda", 1, 1, 95); // ajusta valores
    }
}
