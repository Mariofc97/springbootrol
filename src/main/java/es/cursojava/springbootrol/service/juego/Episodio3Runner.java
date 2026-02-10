package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.episodios.Episodio3ElBosqueOscuro;

public class Episodio3Runner implements EpisodioRunner {

	private final Episodio3ElBosqueOscuro episodio = new Episodio3ElBosqueOscuro();
	@Override
	public int numero() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int ejecutar(Personaje p, AccionesEpisodio acciones) {
		// TODO Auto-generated method stub
		episodio.episodio3ElBosqueOscuro(p, acciones);
		return 4; // cuando haya un cuarto episodio ponemos cuatro
	}

}
