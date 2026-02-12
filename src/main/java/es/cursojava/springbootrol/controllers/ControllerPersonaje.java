package es.cursojava.springbootrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.service.PersonajeService;

@Controller
public class ControllerPersonaje {

	@Autowired
	private PersonajeService personajeService;

	@GetMapping("/personaje_usuario/{id}")
	public String verPersonaje(@PathVariable Long id, @RequestParam(required = false) Long uid, Model model)
			throws ReglaJuegoException {

		Personaje p = personajeService.cargarParaJuego(id);
		model.addAttribute("personaje", p);
		model.addAttribute("uid", uid);

		model.addAttribute("razaIcon", iconoPorRaza(p.getRazaTipo()));

		return "personaje_usuario";
	}
	
	private String iconoPorRaza(String razaTipo) {
	    if (razaTipo == null) return null;

	    String rt = razaTipo.trim().toUpperCase();

	    if (rt.contains("MONGOL")) return "/icons/mongol.png";
	    if (rt.contains("RAPA")) return "/icons/rapa-nui.png";
	    if (rt.contains("TROGLODITA")) return "/icons/troglodita.png";

	    return null;
	}
}