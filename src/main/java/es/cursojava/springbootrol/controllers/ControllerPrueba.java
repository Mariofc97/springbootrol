package es.cursojava.springbootrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.episodios.Episodio1Prueba;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.service.EpisodioService;
import es.cursojava.springbootrol.service.PersonajeService;

@Controller
public class ControllerPrueba {

	@Autowired
	private PersonajeService personajeService;

	@Autowired
	private EpisodioService episodioService;
	// Mostrar pantalla previa del episodio (opcional)
	@GetMapping("/episodio1/{id}")
	public String mostrarPantallaEpisodio(@PathVariable Long id,
	                                      @RequestParam(required=false) Long uid,
	                                      Model model) throws ReglaJuegoException {
	    Personaje p = personajeService.cargarParaJuego(id);
	    model.addAttribute("personaje", p);
	    model.addAttribute("uid", uid);
	    return "episodio1";
	}

	/**
	 * Cómo se usa en el controller para persistir acciones episodio java
	 * 
	 * AccionesEpisodio acciones = new AccionesEpisodio(personaje);
	 * 
	 * episodio1Prueba.episodio1(personaje, acciones);
	 * 
	 * accionesEpisodioRepository.save(acciones);
	 * 
	 * model.addAttribute("acciones", acciones.getLog());
	 * 
	 * @throws ReglaJuegoException
	 **/

	// Ejecutar episodio 1
	@PostMapping("/episodio1/{id}/jugar")
	public String ejecutarEpisodio(@PathVariable Long id,
	                               @RequestParam(required=false) Long uid,
	                               Model model) throws ReglaJuegoException {

	    AccionesEpisodio acciones = episodioService.jugarEpisodioActual(id);

	    // Si quieres recargar el personaje actualizado para mostrarlo:
	    Personaje p = personajeService.cargarParaJuego(id);

	    model.addAttribute("personaje", p);
	    model.addAttribute("acciones", acciones.getLog());
	    model.addAttribute("uid", uid);

	    return "episodio1_resultado";
	}
}
