package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.episodios.Episodio4Rio;
import es.cursojava.springbootrol.entities.episodios.Episodio5JefeCueva;

public class Episodio5Runner implements EpisodioRunner {

	private final Episodio5JefeCueva episodio = new Episodio5JefeCueva();
	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public int ejecutar(Personaje p, AccionesEpisodio acciones) {
		// TODO Auto-generated method stub
		episodio.episodio5(p,acciones);
		return 1; // cuando haya un cuarto episodio ponemos cuatro
	}

}
