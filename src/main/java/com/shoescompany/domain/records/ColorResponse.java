package com.shoescompany.domain.records;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta que representa un color")
public record ColorResponse(
        @Schema(description = "ID del color", example = "1")
        Long id,

        @Schema(description = "Nombre del color", example = "Violeta")
        String color
) {}
