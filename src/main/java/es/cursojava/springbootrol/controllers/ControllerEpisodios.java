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
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.service.EpisodioService;
import es.cursojava.springbootrol.service.PersonajeService;

@Controller
public class ControllerEpisodios {

	@Autowired
	private PersonajeService personajeService;

	@Autowired
	private EpisodioService episodioService;
	
	@GetMapping("/episodio/actual/{id}")
	public String irAEpisodioActual(@PathVariable Long id,
	                                @RequestParam(required=false) Long uid,
	                                Model model) throws ReglaJuegoException {

	    Personaje p = personajeService.cargarParaJuego(id);
	    model.addAttribute("personaje", p);
	    model.addAttribute("uid", uid);

	    int ep = p.getEpisodioActual();
	    return "episodio" + ep; // episodio1, episodio2, episodio3...
	}
	@PostMapping("/episodio/actual/{id}/jugar")
	public String jugarEpisodioActual(@PathVariable Long id,
	                                  @RequestParam(required=false) Long uid,
	                                  Model model) throws ReglaJuegoException {

	    AccionesEpisodio acciones = episodioService.jugarEpisodioActual(id);
	    Personaje p = personajeService.cargarParaJuego(id);

	    model.addAttribute("personaje", p);
	    model.addAttribute("acciones", acciones.getLog());
	    model.addAttribute("uid", uid);
	    model.addAttribute("pid", id); // ✅

	    int episodioJugado = Math.max(1, p.getEpisodioActual() - 1);
	    return "episodio" + episodioJugado + "_resultado";
	}


}
