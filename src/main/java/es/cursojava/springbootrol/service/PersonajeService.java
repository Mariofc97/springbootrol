package es.cursojava.springbootrol.service;

import java.util.List;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;

public interface PersonajeService {
//TODO: HAY QUE USAR DTOs PARA LOS PERSONAJES.
	Personaje crearYGuardar(Long usuarioId, String nombre, String razaTipo);

	Personaje buscarPorId(Long id) throws ReglaJuegoException;

	List<Personaje> listarPorUsuario(Long usuarioId);

	public Personaje actualizar(Personaje p);

	Personaje cargarParaJuego(Long personajeId) throws ReglaJuegoException;

	Personaje sumarExperiencia(Long personajeId, int exp) throws ReglaJuegoException;

	Personaje actualizarNivel(Long personajeId, int nuevoNivel) throws ReglaJuegoException;

}
