package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.Episodio1;

public class Episodio1Runner implements EpisodioRunner {

	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int ejecutar(Personaje personaje) {
		// TODO Auto-generated method stub
		Episodio1.episodio1(personaje);
		return 2;
	}

}
