package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.episodios.AccionesEpisodio;

public interface AccionesEpisodioRepository extends JpaRepository<AccionesEpisodio, Long> {
}
/**
 * Cómo se usa en el controller para persistir acciones episodio
*java

*AccionesEpisodio acciones = new AccionesEpisodio(personaje);

*episodio1Prueba.episodio1(personaje, acciones);

*accionesEpisodioRepository.save(acciones);

*model.addAttribute("acciones", acciones.getLog());
**/