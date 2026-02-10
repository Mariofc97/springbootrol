package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.episodios.Episodio1;
import es.cursojava.springbootrol.entities.episodios.Episodio2;

public class Episodio2Runner implements EpisodioRunner {
	
	private final Episodio2 episodio = new Episodio2();

	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int ejecutar(Personaje p, AccionesEpisodio acciones) {
		// TODO Auto-generated method stub
		episodio.episodio2(p,acciones);
		return 3;
	}

}
