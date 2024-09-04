package com.shoescompany.domain.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear o actualizar un color")
public class ColorDTO {

    @NotBlank(message = "El color no puede estar en blanco")
    @NotNull(message = "El color no puede ser nulo")
    @Size(min = 1, max = 20, message = "El color debe tener entre 1 y 20 caracteres")
    @Schema(description = "Nombre del color", example = "Violeta", required = true, maxLength = 20)
    private String color;

}
