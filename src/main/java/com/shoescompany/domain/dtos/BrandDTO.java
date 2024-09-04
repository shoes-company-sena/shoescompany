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
@Schema(description = "DTO para crear o actualizar una marca")
public class BrandDTO {


    @NotBlank(message = "La marca no puede estar en blanco")
    @NotNull(message = "La marca no puede ser nula")
    @Size(min = 1, max = 50, message = "La marca debe tener entre 1 y 50 caracteres")
    @Schema(description = "Nombre de la marca", example = "Nike", required = true, maxLength = 50)
    private String brand;
}
