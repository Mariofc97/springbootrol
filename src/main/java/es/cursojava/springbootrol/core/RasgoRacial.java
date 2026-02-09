package es.cursojava.springbootrol.core;

import es.cursojava.springbootrol.entities.Personaje;

public interface RasgoRacial {
	
	void aplicarBonos(Personaje p);
	
	String descripcionRasgos();

}
