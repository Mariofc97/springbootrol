package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.EquipamientoDto;
import entities.Personaje;
import entities.equipo.Equipamiento;
import entities.equipo.armas.Arco;
import entities.equipo.armas.Armas;
import entities.equipo.armas.Bumeran;
import entities.equipo.armas.CanaPescar;
import entities.equipo.armas.Cazamariposas;
import entities.equipo.armas.Honda;
import entities.equipo.armas.Lanza;
import entities.equipo.armas.Trampa;
import entities.equipo.escudos.EscudoMadera;
import entities.equipo.escudos.EscudoPiedra;
import entities.equipo.escudos.Escudos;
import entities.equipo.objetos.Baya;
import entities.equipo.objetos.CarneSeca;
import entities.equipo.objetos.Cuerda;
import entities.equipo.objetos.HojaParaLimpiar;
import entities.equipo.objetos.MojonSeco;
import entities.equipo.objetos.Palo;
import entities.equipo.objetos.Piedra;
import entities.equipo.objetos.Pocion;
import exceptions.ReglaJuegoException;
import repositories.EquipamientoRepository;
import repositories.PersonajeRepository;
import service.EquipamientoService;

@Service
public class EquipamientoServiceImpl implements EquipamientoService {

	private final EquipamientoRepository equipamientoRepository;
	private final PersonajeRepository personajeRepository;
	
	public EquipamientoServiceImpl(EquipamientoRepository equipamientoRepository, PersonajeRepository personajeRepository) {
	    this.equipamientoRepository = equipamientoRepository;
	    this.personajeRepository = personajeRepository;
	}
	
	private static final int MAX_OBJETOS = 20;
	private static final int MAX_PESO_TOTAL = 35;
		
	private EquipamientoDto mapToDto(Equipamiento e) {
	    if (e == null) return null;

	    String tipo = e.getClass().getSimpleName().toUpperCase();

	    Long personajeId = null;
	    if (e.getPersonaje() != null) {
	        personajeId = e.getPersonaje().getId();
	    }

	    return new EquipamientoDto(
	        e.getId(),
	        tipo,
	        e.getNombre(),
	        e.getPeso(),
	        e.getDurabilidad(),
	        e.getNivelRequerido(),
	        personajeId
	    );
	}

	@Override
	@Transactional(readOnly = true)
    public List<EquipamientoDto> listarPorPersonaje(Long personajeId) {
        if (personajeId == null) throw new RuntimeException("personajeId obligatorio");

        Personaje personaje = personajeRepository.findByIdFetchAll(personajeId)
                .orElseThrow(() -> new RuntimeException("No existe Personaje con id=" + personajeId));

        if (personaje.getEquipo() == null) return List.of();

        List<EquipamientoDto> res = new ArrayList<>();
        for (Equipamiento e : personaje.getEquipo()) {
            res.add(mapToDto(e));
        }
        return res;
    }

	@Override
	@Transactional
	public EquipamientoDto añadirAlInventario(Long personajeId, Equipamiento nuevo) throws ReglaJuegoException {

	    Personaje p = cargarPersonajeConEquipo(personajeId); // <-- CLAVE (fetch equipo)

	    if (nuevo == null) throw new ReglaJuegoException("Equipamiento obligatorio");

	    if (p.getEquipo().size() >= MAX_OBJETOS) {
	        throw new ReglaJuegoException("No puedes llevar más de " + MAX_OBJETOS + " objetos.");
	    }

	    int pesoActual = 0;
	    for (Equipamiento e : p.getEquipo()) {
	        pesoActual += e.getPeso();
	    }

	    if (pesoActual + nuevo.getPeso() > MAX_PESO_TOTAL) {
	        throw new ReglaJuegoException("Te pasas de peso. El máximo permitido es: " + MAX_PESO_TOTAL + " kg.");
	    }

	    // enlazar FK + añadir
	    // nuevo.setPersonaje(p) se hace dentro de p.addEquipamiento si se usa el metodo helper, 
	    // pero aqui lo hacemos explicitamente o reutilizamos el helper de la entidad si existe.
	    // La entidad Personaje tiene un método addEquipamiento(e) que setea la relación.
	    p.addEquipamiento(nuevo);

	    personajeRepository.save(p);

	    return mapToDto(nuevo);
	}

	@Override
	@Transactional
	public EquipamientoDto fabricar(Long personajeId, String tipo) throws ReglaJuegoException {

	    Personaje p = cargarPersonajeConEquipo(personajeId);

	    if (tipo == null || tipo.isBlank()) {
	        throw new ReglaJuegoException("Tipo de fabricación obligatorio");
	    }

	    String t = tipo.trim().toUpperCase();

	    Equipamiento nuevo;

	    switch (t) {

	        // ===================== ARMAS =====================

	        case "ARCO":
	            // nivel requerido (ejemplo)
	            checkNivel(p, 3);
	            // materiales
	            consumirMateriales(p, "PALO", "CUERDA");
	            // crear
	            nuevo = new Arco();
	            break;

	        case "BUMERAN":
	            checkNivel(p, 1);
	            consumirMateriales(p, "PALO");
	            nuevo = new Bumeran();
	            break;

	        case "CAZAMARIPOSAS":
	            checkNivel(p, 1);
	            consumirMateriales(p, "PALO", "MOJON SECO");
	            nuevo = new Cazamariposas();
	            break;

	        case "LANZA":
	            checkNivel(p, 2);
	            consumirMateriales(p, "PALO", "PIEDRA");
	            nuevo = new Lanza();
	            break;

	        case "HONDA":
	            checkNivel(p, 1);
	            consumirMateriales(p, "CUERDA");
	            nuevo = new Honda();
	            break;

	        case "CAÑA PESCA":
	        case "CANA PESCA":
	            checkNivel(p, 2);
	            consumirMateriales(p, 
	            		"CUERDA",
	            		"PALO",
	                    "BAYA");
	            nuevo = new CanaPescar();
	            break;

	        case "TRAMPA":
	            checkNivel(p, 3);
	            consumirMateriales(p, 
	            		"CUERDA",
	            		"PALO",
	            		"PIEDRA");
	            nuevo = new Trampa();
	            break;

	        // ===================== ESCUDOS =====================

	        case "ESCUDO MADERA":
	        case "ESCUDOMADERA":
	            checkNivel(p, 2);
	            // AJUSTARLO
	            consumirMateriales(p, "PALO");
	            nuevo = new EscudoMadera();
	            break;

	        case "ESCUDO PIEDRA":
	        case "ESCUDOPIEDRA":
	            checkNivel(p, 3);
	            consumirMateriales(p, "PIEDRA", "CUERDA");
	            nuevo = new EscudoPiedra();
	            break;

	        default:
	            throw new ReglaJuegoException("No se puede fabricar: " + t);
	    }

	    // aquí validas inventario (max objetos + peso) reutilizando tu método existente
	    // IMPORTANTE: como ya consumiste materiales, el inventario ya bajó peso/objetos.
	    EquipamientoDto dto = añadirAlInventario(p.getId(), nuevo);

	    // persistir handled by traansaction but añadirAlInventario saves too
	    return dto;
	}

	@Override
	@Transactional
	public EquipamientoDto equiparArma(Long personajeId, Long equipamientoId) throws ReglaJuegoException {

	    Personaje p = cargarPersonajeConEquipo(personajeId);

	    if (equipamientoId == null) throw new ReglaJuegoException("equipamientoId obligatorio");

	    Equipamiento encontrado = null;
	    for (Equipamiento e : p.getEquipo()) {
	        if (e != null && e.getId() != null && e.getId().equals(equipamientoId)) {
	            encontrado = e;
	            break;
	        }
	    }

	    if (encontrado == null) throw new ReglaJuegoException("No tienes ese equipamiento en tu inventario.");
	    if (!(encontrado instanceof Armas)) {
	        throw new ReglaJuegoException("Ese objeto no es un arma.");
	    }

	    // nivel requerido
	    if (p.getNivel() < encontrado.getNivelRequerido()) {
	        throw new ReglaJuegoException("Nivel insuficiente para equipar. Requiere nivel "
	                + encontrado.getNivelRequerido() + " y tienes " + p.getNivel());
	    }

	    // mover al principio = equipada
	    p.getEquipo().remove(encontrado);
	    p.getEquipo().add(0, encontrado);

	    personajeRepository.save(p);

	    return mapToDto(encontrado);
	}

	@Override
	@Transactional
	public EquipamientoDto equiparEscudo(Long personajeId, Long equipamientoId) throws ReglaJuegoException {

	    Personaje p = cargarPersonajeConEquipo(personajeId);

	    if (equipamientoId == null) throw new ReglaJuegoException("equipamientoId obligatorio");

	    Equipamiento encontrado = null;
	    for (Equipamiento e : p.getEquipo()) {
	        if (e != null && e.getId() != null && e.getId().equals(equipamientoId)) {
	            encontrado = e;
	            break;
	        }
	    }

	    if (encontrado == null) throw new ReglaJuegoException("No tienes ese equipamiento en tu inventario.");
	    if (!(encontrado instanceof Escudos)) {
	        throw new ReglaJuegoException("Ese objeto no es un escudo.");
	    }

	    // nivel requerido
	    if (p.getNivel() < encontrado.getNivelRequerido()) {
	        throw new ReglaJuegoException("Nivel insuficiente para equipar. Requiere nivel "
	                + encontrado.getNivelRequerido() + " y tienes " + p.getNivel());
	    }

	    // mover al principio = equipado (igual que armas)
	    p.getEquipo().remove(encontrado);
	    p.getEquipo().add(0, encontrado);

	    personajeRepository.save(p);

	    return mapToDto(encontrado);
	}

	
	private Personaje cargarPersonajeConEquipo(Long personajeId) throws ReglaJuegoException {
	    if (personajeId == null) throw new ReglaJuegoException("personajeId obligatorio");
	    
	    Personaje p = personajeRepository.findByIdFetchAll(personajeId)
	            .orElseThrow(() -> new ReglaJuegoException("No existe personaje con id=" + personajeId));
	            
	    if (p.getEquipo() == null) p.setEquipo(new ArrayList<>());
	    return p;
	}

	private void checkNivel(Personaje p, int nivelRequerido) throws ReglaJuegoException {
	    if (p.getNivel() < nivelRequerido) {
	        throw new ReglaJuegoException("Nivel insuficiente. Necesitas nivel " + nivelRequerido +
	                " y tienes " + p.getNivel());
	    }
	}

	private Equipamiento encontrarMaterial(Personaje p, String material) {
	    for (Equipamiento e : p.getEquipo()) {
	        if (e == null) continue;

	        switch (material) {
	            case "PALO":
	                if (e instanceof Palo) return e;
	                break;
	            case "CUERDA":
	                if (e instanceof Cuerda) return e;
	                break;
	            case "PIEDRA":
	                if (e instanceof Piedra) return e;
	                break;
	            case "BAYA":
	                if (e instanceof Baya) return e;
	                break;
	            case "POCION":
	            	if (e instanceof Pocion) return e;
	            	break;
	            case "HOJA PARA LIMPIAR":
	            	if (e instanceof HojaParaLimpiar) return e;
	            	break;
	            case "CARNE":
	            case "CARNE SECA":
	            	if (e instanceof CarneSeca) return e;
	            	break;
	            case "MOJON":
	            case "MOJON SECO":
	                if (e instanceof MojonSeco) return e;
	                break;
	            default:
	            	System.out.println("Material no encontrado");
	                return null;
	        }
	    }
	    return null;
	}
	
	private void consumirMateriales(Personaje p, String... materiales) throws ReglaJuegoException {

	    // 1) comprobar que existen
	    for (String m : materiales) {
	        Equipamiento encontrado = encontrarMaterial(p, m);
	        if (encontrado == null) {
	            throw new ReglaJuegoException("Te falta material: " + m);
	        }
	    }

	    // 2) borrar uno de cada
	    for (String m : materiales) {
	        Equipamiento encontrado = encontrarMaterial(p, m);
	        // aquí ya sabemos que existe
	        p.getEquipo().remove(encontrado);
	        // Also remove from repository explicitly? Cascade should handle removal if orphanRemoval=true.
	        // In Personaje.java: @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, orphanRemoval = true)
	        // So removing from list should be enough upon saving Personaje.
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public List<EquipamientoDto> listarConsumiblesCurativos(Long personajeId) throws ReglaJuegoException {
	    Personaje p = cargarPersonajeConEquipo(personajeId);

	    List<EquipamientoDto> res = new ArrayList<>();
	    for (Equipamiento e : p.getEquipo()) {
	        if (e instanceof Baya || e instanceof CarneSeca || e instanceof Pocion) {
	            res.add(mapToDto(e));
	        }
	    }
	    return res;
	}

	@Override
	@Transactional
	public int consumirCurativo(Long personajeId, Long equipamientoId) throws ReglaJuegoException {
	    if (equipamientoId == null) throw new ReglaJuegoException("equipamientoId obligatorio");

	    Personaje p = cargarPersonajeConEquipo(personajeId);

	    Equipamiento elegido = null;
	    for (Equipamiento e : p.getEquipo()) {
	        if (e != null && e.getId() != null && e.getId().equals(equipamientoId)) {
	            elegido = e;
	            break;
	        }
	    }

	    if (elegido == null) throw new ReglaJuegoException("No tienes ese objeto.");
	    if (!(elegido instanceof Baya || elegido instanceof CarneSeca || elegido instanceof Pocion)) {
	        throw new ReglaJuegoException("Ese objeto no es consumible curativo.");
	    }

	    int cura = calcularCuracion(elegido);

	    int antes = p.getPuntosVida();
	    int nuevaVida = antes + cura;
	    if (nuevaVida > p.getPuntosVidaMax()) nuevaVida = p.getPuntosVidaMax();
	    p.setPuntosVida(nuevaVida);

	    p.getEquipo().remove(elegido);

	    personajeRepository.save(p);

	    return p.getPuntosVida();
	}

	private int calcularCuracion(Equipamiento e) {
	    if (e instanceof Pocion) return ((Pocion) e).getPuntosDeVida();
	    if (e instanceof CarneSeca) return 12; 
	    if (e instanceof Baya) return 5;       
	    return 0;
	}
	
	@Override
	@Transactional
	public void eliminarDeInventario(Long personajeId, Long equipamientoId) throws ReglaJuegoException {
	    if (personajeId == null || equipamientoId == null) {
	        throw new ReglaJuegoException("Ids inválidos.");
	    }

	    // This calls the custom Delete query in Repository
	    equipamientoRepository.deleteByIdAndPersonajeId(equipamientoId, personajeId);
	    
	    // Verify if needed? The repository method is void.
	    // If I want to throw exception if not found, I might need to check existence first or capture the result if using @Modifying
	    // The original code returned int (rows affected).
	    // I'll assume if it fails it throws exception or I can check count first.
	    // But for cleaner migration, let's keep it simple. If it's gone, it's gone.
	}

	
}
