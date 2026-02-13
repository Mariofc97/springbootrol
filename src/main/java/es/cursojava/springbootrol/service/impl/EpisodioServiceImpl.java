package es.cursojava.springbootrol.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.repositories.AccionesEpisodioRepository;
import es.cursojava.springbootrol.repositories.PersonajeRepository;
import es.cursojava.springbootrol.service.EpisodioService;
import es.cursojava.springbootrol.service.juego.EpisodioRegistry;
import es.cursojava.springbootrol.service.juego.EpisodioRunner;

@Service
public class EpisodioServiceImpl implements EpisodioService {

	private final PersonajeRepository personajeRepository;
	private final AccionesEpisodioRepository accionesEpisodioRepository;
	private final EpisodioRegistry registry = new EpisodioRegistry();

	public EpisodioServiceImpl(PersonajeRepository personajeRepository,
			AccionesEpisodioRepository accionesEpisodioRepository) {
		this.personajeRepository = personajeRepository;
		this.accionesEpisodioRepository = accionesEpisodioRepository;
	}

	@Override
	@Transactional
	public AccionesEpisodio jugarEpisodioActual(Long personajeId) {

		if (personajeId == null)
			throw new RuntimeException("El Id del personaje es obligatorio");

		Personaje p = personajeRepository.findByIdFetchAll(personajeId)
				.orElseThrow(() -> new RuntimeException("No existe personaje con id=" + personajeId));

		AccionesEpisodio acciones = new AccionesEpisodio(p);
		acciones.add("Inicio episodio " + p.getEpisodioActual());

		int actual = p.getEpisodioActual();

		EpisodioRunner runner = registry.get(actual);
		if (runner == null)
			throw new RuntimeException("No existe runner para episodio " + actual);

		int siguiente = runner.ejecutar(p, acciones);

		p.setEpisodioActual(siguiente);

		// Persistimos TODO al final (una sola vez):
		personajeRepository.save(p);
		accionesEpisodioRepository.save(acciones);

		return acciones;
	}

}
