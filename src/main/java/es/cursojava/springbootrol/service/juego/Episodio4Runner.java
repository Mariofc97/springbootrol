package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.episodios.Episodio4Rio;

public class Episodio4Runner implements EpisodioRunner {

	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int ejecutar(Personaje p, AccionesEpisodio acciones) {
		// TODO Auto-generated method stub
		Episodio4Rio.episodio4Rio(p, acciones);
		return 5; // cuando haya un cuarto episodio ponemos cuatro
	}

}
