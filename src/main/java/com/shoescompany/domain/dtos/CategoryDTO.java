package com.shoescompany.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar una categoria")
public class CategoryDTO {


    @NotNull(message = "La categoría no puede ser nula")
    @NotBlank(message = "La categoría no puede estar en blanco")
    @Size(min = 1, max = 50, message = "La categoría debe tener entre 1 y 50 caracteres")
    @Schema(description = "Nombre de la categoría", example = "Deportivos", required = true, maxLength = 50)
    private String category;

    @NotBlank(message = "La imagen de la categoría no puede estar en blanco")
    @Size(min = 1, max = 255, message = "La imagen de la categoría debe tener entre 1 y 255 caracteres")
    @Schema(description = "URL de la imagen asociada a la categoría", example = "https://example.com/image.jpg", maxLength = 255)
    private String image;
}
