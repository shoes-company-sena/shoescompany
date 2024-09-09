package com.shoescompany.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotBlank(message = "El nombre del cliente no puede estar en blanco")
    @Schema(description = "Nombre del cliente que realiza el pedido", example = "Juan Pérez", required = true, maxLength = 100)
    private String customerName;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Schema(description = "Cantidad de productos en el pedido", example = "5", required = true)
    private Integer quantity;

    @NotNull(message = "La lista de productos no puede ser nula")
    @Schema(description = "IDs de los productos en el pedido", example = "[1, 2, 3]", required = true)
    private Set<Long> productIds;

    // Getters and setters
}

