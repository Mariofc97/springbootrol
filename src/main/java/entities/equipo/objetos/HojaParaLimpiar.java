package entities.equipo.objetos;

import entities.equipo.Equipamiento;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;




@Entity
@DiscriminatorValue("HOJA_PARA_LIMPIAR")
public class HojaParaLimpiar extends Equipamiento {
	
public HojaParaLimpiar() {
	super("Hoja para limpiar", 1, 1, 10); // ajusta valores
}
}
