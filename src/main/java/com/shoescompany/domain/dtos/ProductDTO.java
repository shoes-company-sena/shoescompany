package com.shoescompany.domain.dtos;

import com.shoescompany.domain.entities.Color;
import com.shoescompany.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar un producto")
public class ProductDTO {

    @Schema(description = "Nombre del producto", example = "Zapatos Deportivos")
    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Size(min = 1, max = 50, message = "El nombre del producto debe tener entre 1 y 50 caracteres")
    private String product;

    @Schema(description = "Descripción del producto", example = "Zapatos deportivos para correr")
    @NotBlank(message = "La descripción del producto no puede estar en blanco")
    @Size(min = 1, max = 500, message = "La descripción del producto debe tener entre 1 y 500 caracteres")
    private String description;

    @Schema(description = "Género del producto", example = "MALE")
    @NotNull(message = "El género no puede ser nulo")
    private Gender gender;

    @Schema(description = "ID de la marca", example = "1")
    @NotNull(message = "La marca no puede ser nula")
    private Long brand;

    @Schema(description = "Precio del producto", example = "199.99")
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 10 dígitos en total y 2 decimales")
    private BigDecimal price;

    @Schema(description = "Imagen del producto", example = "zapatos.jpg")
    @NotBlank(message = "La imagen no puede estar en blanco")
    @Size(min = 1, max = 255, message = "La imagen debe tener entre 1 y 255 caracteres")
    private String image;

    @Schema(description = "Talla del producto", example = "42")
    @NotNull(message = "La Talla no puede ser nulo")
    @Min(value = 1, message = "La Talla debe ser mayor o igual a 1")
    @Max(value = 100, message = "La Talla debe ser menor o igual a 100")
    private Integer size;

    @Schema(description = "Colores disponibles para el producto")
    @NotNull(message = "Los colores no pueden ser nulos")
    @Size(min = 1, message = "Debe seleccionar al menos un color")
    private Set<Long> colors;

    @Schema(description = "ID de la categoria", example = "1")
    @NotNull(message = "La categoria no puede ser nula")
    private Long category; // Usar Long en lugar de Brand



}
