package es.cursojava.springbootrol.service;

import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;

public interface EpisodioService {

	AccionesEpisodio jugarEpisodioActual(Long personajeId);
}
