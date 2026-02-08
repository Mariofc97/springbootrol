package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;

public interface EpisodioRunner {

	int numero();
	int ejecutar(Personaje p, AccionesEpisodio acciones); 
}
