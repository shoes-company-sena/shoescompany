package com.shoescompany.infrastructure.configs;

import com.shoescompany.domain.dtos.ApiResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerConfig {


//    Se configuran la estructura a la hora de capturar una exception

    // Esta es para los generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleException(Exception e) {
        return ResponseEntity.ok(
                new ApiResponseDTO<>(e.getMessage(), false, null)
        );
    }

// Configuracion para la libreria Validation y exprese el error en la validacion del campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Hubo un error en la validacion de campos", false, errors)
        );
    }

}
