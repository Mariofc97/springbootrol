package es.cursojava.springbootrol.model.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CrearPersonajeRequest {
    @NotNull public Long usuarioId;
    @NotBlank public String nombre;
    @NotBlank public String raza;
}

