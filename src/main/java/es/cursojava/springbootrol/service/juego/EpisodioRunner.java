package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;

public interface EpisodioRunner {

	int numero();
	int ejecutar(Personaje personaje); // devuelve el siguiente episodio
}
