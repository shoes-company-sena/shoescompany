package com.shoescompany.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(hidden = true)
public class ApiResponseDTO<T> {


    public static final String DATA_SAVED = "Datos guardados de: ";
    public static final String DATA_UPDATED = "Datos actualizados de: ";


    private String message;
    private boolean state;
    private T data;
}
