package com.shoescompany.domain.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para crear contact")
public class ContactDTO {



    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    @Schema(description = "Nombre de la persona que contacta", example = "Juan Pérez", required = true, maxLength = 100)
    private String name;

    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico debe tener como máximo 100 caracteres")
    @Schema(description = "Correo electrónico del contacto", example = "juan.perez@example.com", required = true, maxLength = 100)
    private String email;

    @NotBlank(message = "El mensaje no puede estar en blanco")
    @Size(min = 1, max = 500, message = "El mensaje debe tener entre 1 y 500 caracteres")
    @Schema(description = "Mensaje del contacto", example = "Estoy interesado en sus productos.", required = true, maxLength = 500)
    private String message;

    @Pattern(regexp = "^\\d{1,10}$", message = "El teléfono debe ser un número entero de hasta 10 dígitos")
    @Schema(description = "Teléfono del contacto", example = "+1234567890", maxLength = 20)
    private BigInteger phone;


}
