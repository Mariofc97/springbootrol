package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.Episodio3ElBosqueOscuro;

public class Episodio3Runner implements EpisodioRunner {

	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int ejecutar(Personaje personaje) {
		// TODO Auto-generated method stub
		Episodio3ElBosqueOscuro.episodio3ElBosqueOscuro(personaje);
		return 3; // cuando haya un cuarto episodio ponemos cuatro
	}

}
