package com.shoescompany.domain.records;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta que representa una marca")
public record BrandResponse(
        @Schema(description = "ID de la marca", example = "1")
        Long id,

        @Schema(description = "Nombre de la marca", example = "Nike")
        String brand
) {}
