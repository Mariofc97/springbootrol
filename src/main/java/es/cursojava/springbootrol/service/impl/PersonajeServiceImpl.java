package es.cursojava.springbootrol.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.Usuario;
import es.cursojava.springbootrol.entities.raza.Mongol;
import es.cursojava.springbootrol.entities.raza.RapaNui;
import es.cursojava.springbootrol.entities.raza.Raza;
import es.cursojava.springbootrol.entities.raza.Troglodita;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.repositories.PersonajeRepository;
import es.cursojava.springbootrol.repositories.UsuarioRepository;
import es.cursojava.springbootrol.service.PersonajeService;

@Service
public class PersonajeServiceImpl implements PersonajeService {

	private final PersonajeRepository personajeRepository;
	private final UsuarioRepository usuarioRepository;
	
	public PersonajeServiceImpl(PersonajeRepository personajeRepository, UsuarioRepository usuarioRepository) {
	    this.personajeRepository = personajeRepository;
	    this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	@Transactional
	public Personaje crearYGuardar(Long usuarioId, String nombre, String razaTipo) {
		if(usuarioId == null) {
			throw new RuntimeException("El id del usuario es obligatorio");
		}
		if(nombre == null || nombre.isBlank()) {
			throw new RuntimeException("El nombre es obligatorio");
		}
		if(razaTipo == null || razaTipo.isBlank()) {
			throw new RuntimeException("La raza es obligatoria");
		}
		
		Usuario u = usuarioRepository.findById(usuarioId)
		        .orElseThrow(() -> new RuntimeException("No existe usuario con id= " + usuarioId));

		try {
			
			Personaje p = new Personaje(nombre.trim(), razaTipo.trim());
			
			p.setUsuario(u);
			
			// Construimos la raza a partir del texto (LA NO PERSISTENTE)
			Raza raza = construirRaza(razaTipo);
			
			// stats base
			inicializarStatsBase(p);			
			aplicarStatsBaseDeRaza(p, raza);
			
			personajeRepository.save(p);
			
			return p;
		} catch (ReglaJuegoException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Personaje buscarPorId(Long id) throws ReglaJuegoException {
		if(id == null) throw new ReglaJuegoException("El ID es obligatorio");
		
		return personajeRepository.findById(id)
		        .orElseThrow(() -> new ReglaJuegoException("No existe personaje con ID= " + id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Personaje> listarPorUsuario(Long usuarioId) {
	    return personajeRepository.findByUsuarioId(usuarioId);
	}
	
	private Raza construirRaza(String razaTipo) throws ReglaJuegoException {
		String rt = razaTipo.trim().toUpperCase();
		
		switch (rt) {
		case "MONGOL":
			return new Mongol();

		case "RAPA NUI":
			return new RapaNui();

		case "TROGLODITA":
			return new Troglodita();

		default:
				throw new ReglaJuegoException("Raza invalida: " + razaTipo + " (usa MONGOL / RAPA NUI / TROGLODITA");
		}
	}
	
	private void inicializarStatsBase(Personaje p) {
		p.setNivel(1);
		p.setExperiencia(0);
		p.setPuntosVidaMax(100);
		p.setPuntosVida(100);
		p.setPuntosAtaque(5);
		p.setInteligencia(2);
		p.setSuerte(2);
		
	}
	
	private void aplicarStatsBaseDeRaza(Personaje p, Raza raza) {
		p.setPuntosAtaque(p.getPuntosAtaque() + raza.getFuerza());
		p.setInteligencia(p.getInteligencia() + raza.getInteligencia());
		p.setSuerte(p.getSuerte() + raza.getSuerte());
	}

	@Override
	@Transactional
	public Personaje actualizar(Personaje p) {
		return personajeRepository.save(p);
	}

	@Transactional(readOnly = true)
	public Personaje cargarParaJuego(Long id) throws ReglaJuegoException {

	    // 1) Traigo personaje + equipo
	    Personaje p = personajeRepository.findByIdFetchAll(id)
	        .orElseThrow(() -> new ReglaJuegoException("No existe personaje con id " + id));

	    // 2) Traigo personaje + criaturas (otro SELECT, evita multiple bag fetch)
	    Personaje pCriaturas = personajeRepository.findByIdFetchCriaturas(id)
	        .orElseThrow(() -> new ReglaJuegoException("No existe personaje con id " + id));

	    // 3) “Copio” las criaturas al personaje principal
	    p.setCriaturas(pCriaturas.getCriaturas());

	    return p;
	}


	@Override
	@Transactional
	public Personaje sumarExperiencia(Long personajeId, int exp) throws ReglaJuegoException {
	    if (personajeId == null) throw new ReglaJuegoException("ID personaje obligatorio");
	    if (exp <= 0) throw new ReglaJuegoException("La exp debe ser positiva");

	    Personaje p = personajeRepository.findById(personajeId)
	            .orElseThrow(() -> new ReglaJuegoException("No existe personaje con ID=" + personajeId));

	    p.ganarExperiencia(exp); // lógica dominio

	    return p; // Transactional will save changes
	}

	@Override
	@Transactional
	public Personaje actualizarNivel(Long personajeId, int nuevoNivel) throws ReglaJuegoException {
	    if (personajeId == null) throw new ReglaJuegoException("ID personaje obligatorio");
	    if (nuevoNivel < 1) throw new ReglaJuegoException("El nivel debe ser >= 1");

	    Personaje p = personajeRepository.findById(personajeId)
	            .orElseThrow(() -> new ReglaJuegoException("No existe personaje con ID=" + personajeId));

	    p.setNivel(nuevoNivel);
	    return personajeRepository.save(p);
	}


}
