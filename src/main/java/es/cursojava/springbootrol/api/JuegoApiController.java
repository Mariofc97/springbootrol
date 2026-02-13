package es.cursojava.springbootrol.api;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
    name = "Juego (API)",
    description = "CRUD mínimo para pruebas REST con Postman/Swagger sobre personajes e inventario."
)
@SecurityRequirement(name = "basicAuth") // 🔒 Swagger mostrará el candado (si defines basicAuth en OpenAPI config)
@RestController
@RequestMapping("/api")
public class JuegoApiController {

    private final PersonajeService personajeService;
    private final EquipamientoService equipamientoService;

    public JuegoApiController(PersonajeService personajeService, EquipamientoService equipamientoService) {
        this.personajeService = personajeService;
        this.equipamientoService = equipamientoService;
    }

    // ==========================
    // CREATE
    // ==========================
    @Operation(
        summary = "Crear personaje",
        description = """
            Crea un personaje asociado a un usuario existente (usuarioId).
            Requiere autenticación (Basic Auth) para poder probar desde Postman/Swagger.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Personaje creado correctamente",
            content = @Content(schema = @Schema(implementation = PersonajeApiDto.class),
                examples = @ExampleObject(value = """
                {
                  "id": 283,
                  "nombre": "Pepe",
                  "razaTipo": "MONGOL",
                  "nivel": 1,
                  "experiencia": 0,
                  "puntosVida": 100,
                  "puntosVidaMax": 100,
                  "puntosAtaque": 7,
                  "inteligencia": 5,
                  "suerte": 8
                }
                """))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos (validación)",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado (falta Basic Auth)", content = @Content),
        @ApiResponse(responseCode = "403", description = "Prohibido (si hay reglas/roles adicionales)", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/personajes")
    public ResponseEntity<PersonajeApiDto> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para crear el personaje",
            required = true,
            content = @Content(
                schema = @Schema(implementation = CrearPersonajeRequest.class),
                examples = @ExampleObject(value = """
                {
                  "usuarioId": 301,
                  "nombre": "Pepe",
                  "raza": "MONGOL"
                }
                """)
            )
        )
        @Valid @RequestBody CrearPersonajeRequest req
    ) {
        Personaje creado = personajeService.crearYGuardar(req.usuarioId, req.nombre, req.raza);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(creado));
    }

    // ==========================
    // READ
    // ==========================
    @Operation(
        summary = "Ver personaje por id",
        description = "Devuelve los datos principales del personaje (y su estado actual para juego)."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = PersonajeApiDto.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "404", description = "No existe el personaje", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/personajes/{id}")
    public PersonajeApiDto ver(
        @Parameter(description = "ID del personaje", example = "283")
        @PathVariable Long id
    ) throws ReglaJuegoException {
        Personaje p = personajeService.cargarParaJuego(id);
        return toDto(p);
    }

    @Operation(
        summary = "Listar inventario de un personaje",
        description = "Devuelve el inventario del personaje en formato DTO."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = EquipamientoDto.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "404", description = "No existe el personaje", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/personajes/{pid}/inventario")
    public List<EquipamientoDto> inventario(
        @Parameter(description = "ID del personaje", example = "283")
        @PathVariable Long pid
    ) {
        return equipamientoService.listarPorPersonaje(pid);
    }

    // ==========================
    // UPDATE
    // ==========================
    @Operation(
        summary = "Actualizar nivel de un personaje",
        description = """
            Modifica el nivel del personaje (PATCH).
            Si envías nivel 0 o negativo, debería devolver 400 por validación.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nivel actualizado",
            content = @Content(schema = @Schema(implementation = PersonajeApiDto.class))),
        @ApiResponse(responseCode = "400", description = "Nivel inválido", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "404", description = "No existe el personaje", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PatchMapping("/personajes/{id}/nivel")
    public PersonajeApiDto cambiarNivel(
        @Parameter(description = "ID del personaje", example = "283")
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Nuevo nivel del personaje",
            required = true,
            content = @Content(
                schema = @Schema(implementation = UpdateNivelRequest.class),
                examples = {
                    @ExampleObject(name = "Ejemplo OK", value = """
                    { "nivel": 5 }
                    """),
                    @ExampleObject(name = "Ejemplo KO", value = """
                    { "nivel": 0 }
                    """)
                }
            )
        )
        @Valid @RequestBody UpdateNivelRequest req
    ) throws ReglaJuegoException {
        Personaje p = personajeService.actualizarNivel(id, req.nivel);
        return toDto(p);
    }

    // ==========================
    // DELETE
    // ==========================
    @Operation(
        summary = "Eliminar objeto del inventario",
        description = """
            Elimina un equipamiento del inventario del personaje.
            Si el id no existe o no pertenece a ese personaje, lo ideal es devolver 404.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Eliminado correctamente (No Content)"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
        @ApiResponse(responseCode = "404", description = "No existe o no pertenece al personaje", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/personajes/{pid}/inventario/{equipId}")
    public ResponseEntity<Void> tirar(
        @Parameter(description = "ID del personaje", example = "283")
        @PathVariable Long pid,
        @Parameter(description = "ID del equipamiento a eliminar", example = "999")
        @PathVariable Long equipId
    ) throws ReglaJuegoException {
        equipamientoService.eliminarDeInventario(pid, equipId);
        return ResponseEntity.noContent().build();
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
