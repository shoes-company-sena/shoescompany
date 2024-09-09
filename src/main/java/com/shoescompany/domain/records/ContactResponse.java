package com.shoescompany.domain.records;

import java.math.BigInteger;
import io.swagger.v3.oas.annotations.media.Schema;

public record ContactResponse(
        @Schema(description = "Identificador único del contacto", example = "1")
        Long id,

        @Schema(description = "Nombre de la persona que contacta", example = "Juan Pérez")
        String name,

        @Schema(description = "Correo electrónico del contacto", example = "juan.perez@example.com")
        String email,

        @Schema(description = "Mensaje del contacto", example = "Estoy interesado en sus productos.")
        String message,

        @Schema(description = "Teléfono del contacto", example = "1234567890")
        BigInteger phone
) {}
