package es.cursojava.springbootrol.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.model.EquipamientoDto;
import es.cursojava.springbootrol.model.api.CrearPersonajeRequest;
import es.cursojava.springbootrol.model.api.PersonajeApiDto;
import es.cursojava.springbootrol.model.api.UpdateNivelRequest;
import es.cursojava.springbootrol.service.EquipamientoService;
import es.cursojava.springbootrol.service.PersonajeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class JuegoApiController {

    private final PersonajeService personajeService;
    private final EquipamientoService equipamientoService;

    public JuegoApiController(PersonajeService personajeService, EquipamientoService equipamientoService) {
        this.personajeService = personajeService;
        this.equipamientoService = equipamientoService;
    }

    //http://localhost:8085/api/personajes
    // CREATE -> 201
    @PostMapping("/personajes")
    public ResponseEntity<PersonajeApiDto> crear(@Valid @RequestBody CrearPersonajeRequest req) {
        Personaje creado = personajeService.crearYGuardar(req.usuarioId, req.nombre, req.raza);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(creado));
    }

    // READ -> 200
    @GetMapping("/personajes/{id}")
    public PersonajeApiDto ver(@PathVariable Long id) throws ReglaJuegoException {
        Personaje p = personajeService.cargarParaJuego(id);
        return toDto(p);
    }

    // UPDATE -> 200
    @PatchMapping("/personajes/{id}/nivel")
    public PersonajeApiDto cambiarNivel(@PathVariable Long id, @Valid @RequestBody UpdateNivelRequest req)
            throws ReglaJuegoException {
        Personaje p = personajeService.actualizarNivel(id, req.nivel);
        return toDto(p);
    }

    // DELETE -> 204 / 404
    @DeleteMapping("/personajes/{pid}/inventario/{equipId}")
    public ResponseEntity<Void> tirar(@PathVariable Long pid, @PathVariable Long equipId)
            throws ReglaJuegoException {
        equipamientoService.eliminarDeInventario(pid, equipId);
        return ResponseEntity.noContent().build();
    }

    // (extra útil) READ inventario -> 200
    @GetMapping("/personajes/{pid}/inventario")
    public List<EquipamientoDto> inventario(@PathVariable Long pid) {
        return equipamientoService.listarPorPersonaje(pid);
    }

    private PersonajeApiDto toDto(Personaje p) {
        PersonajeApiDto dto = new PersonajeApiDto();
        dto.id = p.getId();
        dto.nombre = p.getNombre();
        dto.razaTipo = p.getRazaTipo();
        dto.nivel = p.getNivel();
        dto.experiencia = p.getExperiencia();
        dto.puntosVida = p.getPuntosVida();
        dto.puntosVidaMax = p.getPuntosVidaMax();
        dto.puntosAtaque = p.getPuntosAtaque();
        dto.inteligencia = p.getInteligencia();
        dto.suerte = p.getSuerte();
        return dto;
    }
}

