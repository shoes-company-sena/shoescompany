package com.shoescompany.domain.records;

import com.shoescompany.domain.entities.Color;
import com.shoescompany.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Set;

@Schema(description = "Respuesta con los detalles de un producto")
public record ProductResponse(
        @Schema(description = "ID del producto", example = "1")
        Long id,

        @Schema(description = "Nombre del producto", example = "Zapatos Deportivos")
        String product,

        @Schema(description = "Descripción del producto", example = "Zapatos deportivos para correr")
        String description,

        @Schema(description = "Género del producto", example = "MALE")
        Gender gender,

        @Schema(description = "Marca del producto")
        String brand,

        @Schema(description = "Precio del producto", example = "199.99")
        BigDecimal price,

        @Schema(description = "Imagen del producto", example = "zapatos.jpg")
        String image,

        @Schema(description = "Tamaño del producto", example = "42")
        Integer size,

/*        @Schema(description = "Colores disponibles para el producto")
        Set<ColorResponse> colors,*/

        @Schema(description = "Categoria a la que pertenece el producto")
        String category
) {}