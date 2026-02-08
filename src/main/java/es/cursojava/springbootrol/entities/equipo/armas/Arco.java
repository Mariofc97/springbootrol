package es.cursojava.springbootrol.entities.equipo.armas;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ARCO")
public class Arco extends Armas {

    public Arco() {
        super(
            "Arco",   // nombre
            3,        // nivelRequerido
            3,        // peso
            60,       // durabilidad
            "FLECHAS",// tipoDaño
            4,        // alcance
            70,       // precision
            6,        // puntosDaño
            10        // probCritico
        );
    }

	@Override
	public String toString() {
		return "Arco [getTipoDaño()=" + getTipoDaño() + ", getAlcance()=" + getAlcance() + ", getPrecision()="
				+ getPrecision() + ", getPuntosDaño()=" + getPuntosDaño() + ", getProbCritico()=" + getProbCritico()
				+ ", toString()=" + super.toString() + ", getId()=" + getId() + ", getPersonaje()=" + getPersonaje()
				+ ", getNombre()=" + getNombre() + ", getNivelRequerido()=" + getNivelRequerido() + ", getPeso()="
				+ getPeso() + ", getDurabilidad()=" + getDurabilidad() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
    
    
    
    
    
    
}
