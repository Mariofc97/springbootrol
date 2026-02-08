package es.cursojava.springbootrol.service.juego;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.episodios.Episodio1Prueba;

public class Episodio1Runner implements EpisodioRunner {

    private final Episodio1Prueba episodio = new Episodio1Prueba();

    @Override
    public int numero() {
        return 1;
    }

    @Override
    public int ejecutar(Personaje p, AccionesEpisodio acciones) {
        episodio.episodio1(p, acciones);
        return 2; // aquí decides el siguiente episodio
    }
}