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

	    
	    if (p.getEpisodioActual() > 5) {
	        model.addAttribute("personaje", p);
	        model.addAttribute("uid", uid);
	        model.addAttribute("pid", id);
	        return "historia_finalizada";  
	    }

	    model.addAttribute("personaje", p);
	    model.addAttribute("uid", uid);

	    int ep = p.getEpisodioActual();
	    return "episodio" + ep;
	}

	@PostMapping("/episodio/actual/{id}/jugar")
	public String jugarEpisodioActual(@PathVariable Long id,
	                                  @RequestParam(required=false) Long uid,
	                                  Model model) throws ReglaJuegoException {

	    Personaje pAntes = personajeService.cargarParaJuego(id);

	    // Si ya está finalizada la historia, no dejamos jugar
	    if (pAntes.getEpisodioActual() > 5) {
	        model.addAttribute("personaje", pAntes);
	        model.addAttribute("uid", uid);
	        model.addAttribute("pid", id);
	        return "historia_finalizada";
	    }

	    // ✅ El episodio que se juega es el que había ANTES de ejecutar
	    int episodioJugado = pAntes.getEpisodioActual();

	    AccionesEpisodio acciones = episodioService.jugarEpisodioActual(id);
	    Personaje pDespues = personajeService.cargarParaJuego(id);

	    model.addAttribute("personaje", pDespues);
	    model.addAttribute("acciones", acciones.getLog());
	    model.addAttribute("uid", uid);
	    model.addAttribute("pid", id);

	    // ✅ Siempre devolvemos el resultado del episodio REAL que se jugó
	    return "episodio" + episodioJugado + "_resultado";
	}




}
