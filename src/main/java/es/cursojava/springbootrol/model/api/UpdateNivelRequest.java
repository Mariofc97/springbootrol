package es.cursojava.springbootrol.model.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateNivelRequest {
    @NotNull @Min(1)
    public Integer nivel;
}

