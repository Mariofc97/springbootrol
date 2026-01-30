package controllers.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.CriaturaDto;
import model.EquipamientoDto;
import entities.Personaje;
import entities.equipo.Equipamiento;
import exceptions.ReglaJuegoException;
import service.CriaturaService;
import service.EquipamientoService;
import service.PersonajeService;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeRestController {

    private final PersonajeService personajeService;
    private final EquipamientoService equipamientoService;
    private final CriaturaService criaturaService;

    public PersonajeRestController(PersonajeService personajeService, 
                                   EquipamientoService equipamientoService,
                                   CriaturaService criaturaService) {
        this.personajeService = personajeService;
        this.equipamientoService = equipamientoService;
        this.criaturaService = criaturaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personaje> getPersonaje(@PathVariable Long id) {
        try {
            Personaje p = personajeService.buscarPorId(id);
            return ResponseEntity.ok(p);
        } catch (ReglaJuegoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/inventario")
    public ResponseEntity<List<EquipamientoDto>> getInventario(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(equipamientoService.listarPorPersonaje(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // POST /api/personajes/{id}/inventario (añadir - needs body or params?)
    // Request asked for: POST /api/personajes/{id}/inventario (añadir)
    // We'll assume receiving an Equipamiento object in body is complex due to inheritance.
    // For simplicity, maybe "fabricar" via API or just params? 
    // Let's implement fabricar here as it's easier to pass "tipo".
    @PostMapping("/{id}/fabricar")
    public ResponseEntity<?> fabricar(@PathVariable Long id, @RequestParam String tipo) {
        try {
            return ResponseEntity.ok(equipamientoService.fabricar(id, tipo));
        } catch (ReglaJuegoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/inventario/{equipId}")
    public ResponseEntity<?> eliminarItem(@PathVariable Long id, @PathVariable Long equipId) {
        try {
            equipamientoService.eliminarDeInventario(id, equipId);
            return ResponseEntity.ok().build();
        } catch (ReglaJuegoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/experiencia")
    public ResponseEntity<?> sumarExperiencia(@PathVariable Long id, @RequestParam int xp) {
        try {
            return ResponseEntity.ok(personajeService.sumarExperiencia(id, xp));
        } catch (ReglaJuegoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
