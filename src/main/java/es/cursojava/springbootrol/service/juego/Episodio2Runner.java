package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.Episodio2;

public class Episodio2Runner implements EpisodioRunner {

	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int ejecutar(Personaje personaje) {
		// TODO Auto-generated method stub
		Episodio2.episodio2(personaje);
		return 3;
	}

}
