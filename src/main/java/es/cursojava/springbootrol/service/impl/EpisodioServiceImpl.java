package es.cursojava.springbootrol.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.repositories.PersonajeRepository;
import es.cursojava.springbootrol.service.EpisodioService;
import es.cursojava.springbootrol.service.juego.EpisodioRegistry;
import es.cursojava.springbootrol.service.juego.EpisodioRunner;

@Service
public class EpisodioServiceImpl implements EpisodioService {

	private final PersonajeRepository personajeRepository;
	private final EpisodioRegistry registry = new EpisodioRegistry(); // Assuming this is non-spring legacy logic we keep
	
	public EpisodioServiceImpl(PersonajeRepository personajeRepository) {
	    this.personajeRepository = personajeRepository;
	}
	
	@Override
	@Transactional
	public Personaje jugarEpisodioActual(Long personajeId) {
	    if (personajeId == null) throw new RuntimeException("El Id del personaje es obligatorio");

	    Personaje p = personajeRepository.findByIdFetchAll(personajeId)
	            .orElseThrow(() -> new RuntimeException("No existe personaje con id=" + personajeId));

	    int actual = p.getEpisodioActual();
	    // Note: EpisodioRegistry/Runner might need dependency injection too if they use services.
	    // The user said "Conserve Utils and Episodios, relocate Utils...".
	    // For now we treat them as POJOs or we might need to inspect them.
	    // But keeping it minimal as requested: logic preserved.
	    EpisodioRunner runner = registry.get(actual);
	    if (runner == null) throw new RuntimeException("No existe runner para episodio " + actual);

	    int siguiente = runner.ejecutar(p);

	    p.setEpisodioActual(siguiente);
	    personajeRepository.save(p);

	    // Return p with fetched data (already fetched at start, and managed)
	    return p;
	}

}
