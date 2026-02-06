package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import entities.Personaje;
import entities.episodios.AccionesEpisodio;
import entities.episodios.Episodio1Prueba;
import service.PersonajeService;

@Controller
public class ControllerPrueba {

	@Autowired
	private PersonajeService personajeService;

	@Autowired
	private Episodio1Prueba episodio1Prueba;

	// Mostrar pantalla previa del episodio (opcional)
	@GetMapping("/episodio1/{id}")
	public String mostrarPantallaEpisodio(@PathVariable Long id, Model model) {
		Personaje p = personajeService.cargarParaJuego(id);
		model.addAttribute("personaje", p);
		return "episodio1";
	}

	// Ejecutar episodio 1
	@PostMapping("/episodio1/{id}/jugar")
	public String ejecutarEpisodio(@PathVariable Long id, Model model) {

		Personaje p = personajeService.cargarParaJuego(id);

		AccionesEpisodio acciones = new AccionesEpisodio();

		episodio1Prueba.episodio1(p, acciones);

		model.addAttribute("personaje", p);
		model.addAttribute("acciones", acciones.getLog());

		return "episodio1_resultado";
	}
}
